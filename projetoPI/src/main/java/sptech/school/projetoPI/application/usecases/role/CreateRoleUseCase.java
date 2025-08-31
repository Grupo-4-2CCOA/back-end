package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateRoleUseCase {
    private final RoleGateway roleGateway;

    public Role execute(Role role) {
        if(roleGateway.existsByName(role.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        role.setId(null);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.POST_SUCCESSFULLY.getMessage());
        return roleGateway.save(role);
    }
}
