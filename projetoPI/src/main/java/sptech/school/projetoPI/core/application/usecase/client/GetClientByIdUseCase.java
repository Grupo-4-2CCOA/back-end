package sptech.school.projetoPI.core.application.usecase.client;

import sptech.school.projetoPI.core.domain.ClientDomain;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;

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
