package sptech.school.projetoPI.core.application.usecases.role;

import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;

public class UpdateRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public UpdateRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleDomain execute(RoleDomain roleDomain, Integer id) {
        if (!roleGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O cargo com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (roleGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O cargo com o ID %d já está inativa".formatted(id)
            );
        }

        if(roleGateway.existsByIdNotAndName(id, roleDomain.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        roleDomain.setId(id);
        roleDomain.setCreatedAt(roleGateway.findById(id).get().getCreatedAt());
        roleDomain.setUpdatedAt(LocalDateTime.now());
        return roleGateway.save(roleDomain);
    }
}
