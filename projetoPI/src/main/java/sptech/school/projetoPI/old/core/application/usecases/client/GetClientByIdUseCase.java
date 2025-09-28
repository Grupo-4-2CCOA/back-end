package sptech.school.projetoPI.old.core.application.usecases.client;

import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

public class GetClientByIdUseCase {

    private final ClientGateway clientGateway;

    public GetClientByIdUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public ClientDomain execute(Integer id) {
        return clientGateway.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Id para cliente não encontrado"));
    }
}
