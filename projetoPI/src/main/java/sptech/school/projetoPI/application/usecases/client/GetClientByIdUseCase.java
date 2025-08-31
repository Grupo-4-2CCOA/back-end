package sptech.school.projetoPI.application.usecases.client;

import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

public class GetClientByIdUseCase {

    private ClientGateway clientGateway;

    public Client execute(Integer id) {
        return clientGateway.findByIdAndActiveTrue(id)
                .map((entity) -> {
                    return entity;
                })
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Id para cliente não encontrado");
                });
    }
}
