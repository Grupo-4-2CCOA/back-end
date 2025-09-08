package sptech.school.projetoPI.core.application.usecase.role;

import sptech.school.projetoPI.core.domain.RoleDomain;
import sptech.school.projetoPI.core.gateway.RoleGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;

import java.time.LocalDateTime;

public class CreateRoleUseCase {
    private final RoleGateway roleGateway;

    public CreateRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleDomain execute(RoleDomain roleDomain) {
        if(roleGateway.existsByName(roleDomain.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        roleDomain.setId(null);
        roleDomain.setCreatedAt(LocalDateTime.now());
        roleDomain.setUpdatedAt(LocalDateTime.now());
        return roleGateway.save(roleDomain);
    }
}
