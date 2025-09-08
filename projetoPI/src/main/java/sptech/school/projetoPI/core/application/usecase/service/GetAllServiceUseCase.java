package sptech.school.projetoPI.core.application.usecase.service;

import sptech.school.projetoPI.core.domain.ServiceDomain;
import sptech.school.projetoPI.core.gateway.ServiceGateway;

import java.util.List;

public class GetAllServiceUseCase {

    private final ServiceGateway serviceGateway;

    public GetAllServiceUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public List<ServiceDomain> execute() {
        return serviceGateway.findAllByActiveTrue();
    }

}
