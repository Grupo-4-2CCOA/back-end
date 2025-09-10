package sptech.school.projetoPI.core.application.usecases.client;

import sptech.school.projetoPI.core.domains.ClientDomain;
import sptech.school.projetoPI.core.gateways.ClientGateway;

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