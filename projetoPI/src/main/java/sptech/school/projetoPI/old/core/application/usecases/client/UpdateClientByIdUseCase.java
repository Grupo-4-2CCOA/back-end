package sptech.school.projetoPI.old.core.application.usecases.client;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.enums.Logs;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;

import java.time.LocalDateTime;

public class UpdateClientByIdUseCase {

    private final ClientGateway clientGateway;

    public UpdateClientByIdUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public ClientDomain execute(ClientDomain clientDomain, Integer id) {
        if (!clientGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.PUT_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if(clientGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (clientGateway.existsByIdNotAndCpf(id, clientDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndEmailIgnoreCase(id, clientDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndPhone(id, clientDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }

        clientDomain.setId(id);
        clientDomain.setCreatedAt(clientGateway.findById(id).get().getCreatedAt());
        clientDomain.setUpdatedAt(LocalDateTime.now());
        return clientGateway.save(clientDomain);
    }
}
