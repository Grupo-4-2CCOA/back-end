package sptech.school.projetoPI.core.application.usecases.service;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class CreateServiceUseCase {

    private final ServiceGateway serviceGateway;

    public CreateServiceUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public ServiceDomain execute(ServiceDomain service) {
        if (serviceGateway.existsByName(service.getName())) {
            throw new EntityConflictException(
                    "Este serviço já está cadastrado"
            );
        }

        if(!serviceGateway.existsById(service.getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(service.getId())
            );
        }

        service.setId(null);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        return serviceGateway.save(service);
    }

}
