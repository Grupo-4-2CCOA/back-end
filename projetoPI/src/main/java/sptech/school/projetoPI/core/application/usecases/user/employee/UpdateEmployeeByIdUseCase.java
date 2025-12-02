package sptech.school.projetoPI.core.application.usecases.user.employee;

import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;

public class UpdateEmployeeByIdUseCase {

    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    public UpdateEmployeeByIdUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
    }

    public UserDomain execute(UserDomain userDomain, Integer id) {
        if (!userGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.PUT_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if(userGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (!roleGateway.existsById(userDomain.getRoleDomain().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted("Role", userDomain.getRoleDomain().getId())
            );
        }

        if (roleGateway.findById(userDomain.getRoleDomain().getId()).get().getName().equals("OWNER") && userGateway.existsByIdNotAndRoleName(id, "OWNER")) {
            throw new EntityConflictException(
                    Logs.PUT_ROLE_CONFLICT.getMessage()
            );
        }

        if (userGateway.existsByIdNotAndCpf(id, userDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }

        if (userGateway.existsByIdNotAndEmailIgnoreCase(id, userDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }

        if (userGateway.existsByIdNotAndPhone(id, userDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }

        userDomain.setId(id);
        userDomain.setCreatedAt(userGateway.findById(id).get().getCreatedAt());
        userDomain.setUpdatedAt(LocalDateTime.now());
        return userGateway.save(userDomain);
    }
}
