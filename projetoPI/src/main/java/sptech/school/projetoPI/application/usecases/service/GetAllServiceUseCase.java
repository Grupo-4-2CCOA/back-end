package sptech.school.projetoPI.application.usecases.service;

import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

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
