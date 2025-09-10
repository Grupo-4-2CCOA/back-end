package sptech.school.projetoPI.core.application.usecases.role;

import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;

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
