package sptech.school.projetoPI.core.application.usecase.client;

import sptech.school.projetoPI.core.domain.ClientDomain;
import sptech.school.projetoPI.core.gateway.ClientGateway;

import java.util.List;

public class GetAllClientsUseCase {
    private final ClientGateway clientGateway;

    public GetAllClientsUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public List<ClientDomain> execute() {
        List<ClientDomain> clientDomains = clientGateway.findAllByActiveTrue();
        return clientDomains;
    }
}