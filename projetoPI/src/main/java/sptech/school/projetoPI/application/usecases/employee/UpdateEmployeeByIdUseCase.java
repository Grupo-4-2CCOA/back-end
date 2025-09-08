package sptech.school.projetoPI.application.usecases.employee;

import sptech.school.projetoPI.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.time.LocalDateTime;

public class UpdateEmployeeByIdUseCase {

    private final ClientGateway clientGateway;
    private final EmployeeGateway employeeGateway;
    private final RoleGateway roleGateway;

    public UpdateEmployeeByIdUseCase(ClientGateway clientGateway, EmployeeGateway employeeGateway, RoleGateway roleGateway) {
        this.clientGateway = clientGateway;
        this.employeeGateway = employeeGateway;
        this.roleGateway = roleGateway;
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

        if (!roleGateway.existsById(employee.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employee.getRole().getId())
            );
        }

        if (roleGateway.findById(employee.getRole().getId()).get().getName().equals("OWNER") && employeeGateway.existsByIdNotAndRoleName(id, "OWNER")) {
            throw new EntityConflictException(
                    Logs.PUT_ROLE_CONFLICT.getMessage()
            );
        }

        if (clientGateway.existsByIdNotAndCpf(id, employee.getCpf())) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndEmailIgnoreCase(id, employee.getEmail())) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndPhone(id, employee.getPhone())) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }

        employee.setId(id);
        employee.setCreatedAt(employeeGateway.findById(id).get().getCreatedAt());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeGateway.save(employee);
    }
}
