package sptech.school.projetoPI.core.application.usecases.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.util.List;

public class GetAllServiceUseCase {

    private final ServiceGateway serviceGateway;

    public GetAllServiceUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public Page<ServiceDomain> execute(Pageable pageable) {
        return serviceGateway.findAllByActiveTrue(pageable);
    }

}
