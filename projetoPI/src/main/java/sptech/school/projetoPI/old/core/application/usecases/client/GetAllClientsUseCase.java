package sptech.school.projetoPI.old.core.application.usecases.client;

import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;

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