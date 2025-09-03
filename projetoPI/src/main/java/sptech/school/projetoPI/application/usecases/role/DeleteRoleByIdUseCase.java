package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteRoleByIdUseCase {
    private final RoleGateway roleGateway;

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

        if (roleGateway.existsByRoleId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes usuários estão relacionados com este cargo: %s".formatted(roleGateway.findAllByRoleId(id))
            );
        }

        Role role = roleGateway.findById(id).get();
        role.setActive(false);
        role.setUpdatedAt(LocalDateTime.now());
        roleGateway.save(role);
    }
}
