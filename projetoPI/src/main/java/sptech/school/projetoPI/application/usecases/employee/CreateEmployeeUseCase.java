package sptech.school.projetoPI.application.usecases.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.user.EmployeeValidationUseCase;
import sptech.school.projetoPI.application.usecases.user.UserValidationUseCase;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.time.LocalDateTime;

public class CreateEmployeeUseCase {

    private final EmployeeGateway repository;
    private final UserValidationUseCase userValidationUseCase;
    private final EmployeeValidationUseCase employeeValidationUseCase;
    private final RoleGateway roleGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateEmployeeUseCase(EmployeeGateway repository, PasswordEncoder passwordEncoder,UserValidationUseCase userValidationUseCase,RoleGateway roleGateway ,EmployeeValidationUseCase employeeValidationUseCase) {
        this.repository = repository;
        this.userValidationUseCase = userValidationUseCase;
        this.roleGateway = roleGateway;
        this.passwordEncoder = passwordEncoder;
        this.employeeValidationUseCase = employeeValidationUseCase;
    }

    public Employee execute(Employee employee) {
        userValidationUseCase.validateUniqueProperties(employee.getCpf(), employee.getEmail(), employee.getPhone());
        employeeValidationUseCase.validateRequestBody(employee);

        if (roleGateway.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    Logs.POST_ROLE_CONFLICT.getMessage()
            );
        }

        String senhaCriptografada = passwordEncoder.encode(employee.getPassword());

        employee.setId(null);
        employee.setPassword(senhaCriptografada);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return repository.save(employee);
    }
}
