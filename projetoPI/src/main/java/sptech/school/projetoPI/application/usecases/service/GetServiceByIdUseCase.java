package sptech.school.projetoPI.application.usecases.service;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

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
