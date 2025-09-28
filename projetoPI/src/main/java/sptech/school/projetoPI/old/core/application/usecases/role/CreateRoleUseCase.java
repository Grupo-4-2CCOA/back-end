package sptech.school.projetoPI.old.core.application.usecases.role;

import sptech.school.projetoPI.old.core.domains.RoleDomain;
import sptech.school.projetoPI.old.core.gateways.RoleGateway;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;

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
