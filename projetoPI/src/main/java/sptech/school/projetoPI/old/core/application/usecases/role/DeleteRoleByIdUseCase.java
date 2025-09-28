package sptech.school.projetoPI.old.core.application.usecases.role;

import sptech.school.projetoPI.old.core.domains.RoleDomain;
import sptech.school.projetoPI.old.core.gateways.RoleGateway;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;

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
