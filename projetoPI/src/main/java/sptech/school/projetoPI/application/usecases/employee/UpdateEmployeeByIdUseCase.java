package sptech.school.projetoPI.application.usecases.employee;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.application.usecases.user.EmployeeValidationUseCase;
import sptech.school.projetoPI.application.usecases.user.UserValidationUseCase;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.time.LocalDateTime;

public class UpdateEmployeeByIdUseCase {

    private final EmployeeGateway employeeGateway;
    private final RoleGateway roleGateway;
    private final UserValidationUseCase userValidationUseCase;
    private final EmployeeValidationUseCase employeeValidationUseCase;

    public UpdateEmployeeByIdUseCase(EmployeeGateway employeeGateway, RoleGateway roleGateway, UserValidationUseCase userValidationUseCase, EmployeeValidationUseCase employeeValidationUseCase) {
        this.employeeGateway = employeeGateway;
        this.roleGateway = roleGateway;
        this.userValidationUseCase = userValidationUseCase;
        this.employeeValidationUseCase = employeeValidationUseCase;
    }

    public Employee execute(Employee employee, Integer id) {
        if (!employeeGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.PUT_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if(employeeGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        /*
        employeeValidationUseCase.validateRequestBody(employee);

         */

        /*
        if (roleGateway.findById(employee.getRole().getId()).get().getName().equals("OWNER") && employeeGateway.existsByIdNotAndRoleName(id, "OWNER")) {
            throw new EntityConflictException(
                    Logs.PUT_ROLE_CONFLICT.getMessage()
            );
        }

         */

        userValidationUseCase.validateUniquePropertiesOnUpdate(id, employee.getCpf(), employee.getEmail(), employee.getPhone());
        employee.setId(id);
        employee.setCreatedAt(employeeGateway.findById(id).get().getCreatedAt());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeGateway.save(employee);
    }
}
