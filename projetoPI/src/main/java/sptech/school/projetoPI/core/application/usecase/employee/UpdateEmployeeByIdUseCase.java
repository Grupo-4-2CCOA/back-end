package sptech.school.projetoPI.core.application.usecase.employee;

import sptech.school.projetoPI.core.application.usecase.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;
import sptech.school.projetoPI.core.gateway.RoleGateway;

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

    public EmployeeDomain execute(EmployeeDomain employeeDomain, Integer id) {
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

        if (!roleGateway.existsById(employeeDomain.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employeeDomain.getRole().getId())
            );
        }

        if (roleGateway.findById(employeeDomain.getRole().getId()).get().getName().equals("OWNER") && employeeGateway.existsByIdNotAndRoleName(id, "OWNER")) {
            throw new EntityConflictException(
                    Logs.PUT_ROLE_CONFLICT.getMessage()
            );
        }

        if (clientGateway.existsByIdNotAndCpf(id, employeeDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndEmailIgnoreCase(id, employeeDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndPhone(id, employeeDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }

        employeeDomain.setId(id);
        employeeDomain.setCreatedAt(employeeGateway.findById(id).get().getCreatedAt());
        employeeDomain.setUpdatedAt(LocalDateTime.now());
        return employeeGateway.save(employeeDomain);
    }
}
