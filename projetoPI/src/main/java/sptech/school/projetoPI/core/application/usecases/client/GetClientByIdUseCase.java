package sptech.school.projetoPI.core.application.usecases.client;

import sptech.school.projetoPI.core.domains.ClientDomain;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

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
