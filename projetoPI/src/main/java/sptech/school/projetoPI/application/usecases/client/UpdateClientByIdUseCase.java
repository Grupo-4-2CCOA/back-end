package sptech.school.projetoPI.application.usecases.client;

import sptech.school.projetoPI.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;

import java.time.LocalDateTime;

public class UpdateClientByIdUseCase {

    private final ClientGateway clientGateway;

    public UpdateClientByIdUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public Client execute(Client client, Integer id) {
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

        if (clientGateway.existsByIdNotAndCpf(id, client.getCpf())) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndEmailIgnoreCase(id, client.getEmail())) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }

        if (clientGateway.existsByIdNotAndPhone(id, client.getPhone())) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }

        client.setId(id);
        client.setCreatedAt(clientGateway.findById(id).get().getCreatedAt());
        client.setUpdatedAt(LocalDateTime.now());
        return clientGateway.save(client);
    }
}
