package sptech.school.projetoPI.application.usecases.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.time.LocalDateTime;

public class CreateEmployeeUseCase {

    private final ClientGateway clientGateway;
    private final EmployeeGateway employeeGateway;
    private final RoleGateway roleGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateEmployeeUseCase(ClientGateway clientGateway, EmployeeGateway employeeGateway, PasswordEncoder passwordEncoder, RoleGateway roleGateway) {
        this.clientGateway = clientGateway;
        this.employeeGateway = employeeGateway;
        this.roleGateway = roleGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee execute(Employee employee) {
        if (clientGateway.existsByCpf(employee.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (clientGateway.existsByEmailIgnoreCase(employee.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if (clientGateway.existsByPhone(employee.getPhone())) {
            throw new ConflictException("Telefone já cadastrado");
        }

        if (!roleGateway.existsById(employee.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employee.getRole().getId())
            );
        }

        if (roleGateway.findById(employee.getRole().getId()).get().getName().equals("OWNER") && employeeGateway.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    Logs.POST_ROLE_CONFLICT.getMessage()
            );
        }

        String senhaCriptografada = passwordEncoder.encode(employee.getPassword());

        employee.setId(null);
        employee.setPassword(senhaCriptografada);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeGateway.save(employee);
    }
}
