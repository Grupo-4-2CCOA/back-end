package sptech.school.projetoPI.core.application.usecases.user.client;

import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;

public class UpdateClientByIdUseCase {

    private final UserGateway userGateway;

    public UpdateClientByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
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
