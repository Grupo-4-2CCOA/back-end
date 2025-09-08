package sptech.school.projetoPI.application.usecases.client;

import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

public class GetClientByIdUseCase {

    private final ClientGateway clientGateway;

    public GetClientByIdUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public Client execute(Integer id) {
        return clientGateway.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Id para cliente não encontrado"));
    }
}
