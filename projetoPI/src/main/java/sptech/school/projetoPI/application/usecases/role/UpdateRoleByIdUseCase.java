package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;

public class UpdateRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public UpdateRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public Role execute(Role role, Integer id) {
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

        if(roleGateway.existsByIdNotAndName(id, role.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        role.setId(id);
        role.setCreatedAt(roleGateway.findById(id).get().getCreatedAt());
        role.setUpdatedAt(LocalDateTime.now());
        return roleGateway.save(role);
    }
}
