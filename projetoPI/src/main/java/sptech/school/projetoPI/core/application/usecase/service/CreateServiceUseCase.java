package sptech.school.projetoPI.core.application.usecase.service;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domain.ServiceDomain;
import sptech.school.projetoPI.core.gateway.ServiceGateway;

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
