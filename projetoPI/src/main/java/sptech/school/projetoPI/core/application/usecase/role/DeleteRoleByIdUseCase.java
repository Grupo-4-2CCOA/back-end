package sptech.school.projetoPI.core.application.usecase.role;

import sptech.school.projetoPI.core.domain.RoleDomain;
import sptech.school.projetoPI.core.gateway.RoleGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;

public class DeleteRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public DeleteRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public void execute(Integer id) {
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

        if (roleGateway.existsById(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes usuários estão relacionados com este cargo: %s".formatted(roleGateway.findAll())
            );
        }

        RoleDomain roleDomain = roleGateway.findById(id).get();
        roleDomain.setActive(false);
        roleDomain.setUpdatedAt(LocalDateTime.now());
        roleGateway.save(roleDomain);
    }
}
