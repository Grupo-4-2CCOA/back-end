package sptech.school.projetoPI.core.application.usecase.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecase.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;
import sptech.school.projetoPI.core.gateway.RoleGateway;

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

    public EmployeeDomain execute(EmployeeDomain employeeDomain) {
        if (clientGateway.existsByCpf(employeeDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (clientGateway.existsByEmailIgnoreCase(employeeDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if (clientGateway.existsByPhone(employeeDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado");
        }

        if (!roleGateway.existsById(employeeDomain.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employeeDomain.getRole().getId())
            );
        }

        if (roleGateway.findById(employeeDomain.getRole().getId()).get().getName().equals("OWNER") && employeeGateway.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    Logs.POST_ROLE_CONFLICT.getMessage()
            );
        }

        String senhaCriptografada = passwordEncoder.encode(employeeDomain.getPassword());

        employeeDomain.setId(null);
        employeeDomain.setPassword(senhaCriptografada);
        employeeDomain.setCreatedAt(LocalDateTime.now());
        employeeDomain.setUpdatedAt(LocalDateTime.now());
        return employeeGateway.save(employeeDomain);
    }
}
