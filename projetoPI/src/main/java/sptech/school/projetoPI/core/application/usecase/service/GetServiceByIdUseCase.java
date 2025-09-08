package sptech.school.projetoPI.core.application.usecase.service;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domain.ServiceDomain;
import sptech.school.projetoPI.core.gateway.ServiceGateway;

public class GetServiceByIdUseCase {

    private final ServiceGateway serviceGateway;

    public GetServiceByIdUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public ServiceDomain execute(Integer id) {
        return serviceGateway.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O serviço com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

}
