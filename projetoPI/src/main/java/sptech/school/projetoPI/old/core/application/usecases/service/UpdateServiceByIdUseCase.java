package sptech.school.projetoPI.old.core.application.usecases.service;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.ServiceDomain;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class UpdateServiceByIdUseCase {

    private final ServiceGateway serviceGateway;

    public UpdateServiceByIdUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public ServiceDomain execute(ServiceDomain service, Integer id) {
        if (!serviceGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (serviceGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O serviço com o ID %d já está inativo".formatted(id)
            );
        }

        if (serviceGateway.existsByIdNotAndName(id, service.getName())) {
            throw new EntityConflictException(
                    "Este serviço já está cadastrado"
            );
        }

        if(!serviceGateway.existsById(service.getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(service.getId())
            );
        }

        service.setId(id);
        service.setCreatedAt(serviceGateway.findById(id).get().getCreatedAt());
        service.setUpdatedAt(LocalDateTime.now());
        return serviceGateway.save(service);
    }

}
