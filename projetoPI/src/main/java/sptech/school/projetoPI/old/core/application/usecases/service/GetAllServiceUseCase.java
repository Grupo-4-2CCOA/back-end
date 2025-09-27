package sptech.school.projetoPI.old.core.application.usecases.service;

import sptech.school.projetoPI.old.core.domains.ServiceDomain;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

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
