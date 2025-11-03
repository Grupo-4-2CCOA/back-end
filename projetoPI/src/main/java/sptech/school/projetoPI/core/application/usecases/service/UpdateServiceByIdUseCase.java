package sptech.school.projetoPI.core.application.usecases.service;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class UpdateServiceByIdUseCase {

    private final ServiceGateway serviceGateway;
    private final FileUploadGateway fileUploadGateway;

    public UpdateServiceByIdUseCase(ServiceGateway serviceGateway, FileUploadGateway fileUploadGateway) {
        this.serviceGateway = serviceGateway;
        this.fileUploadGateway = fileUploadGateway;
    }

    public ServiceDomain execute(ServiceDomain service, Integer id, byte[] imageContent, String originalFileName) {
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

        // ✅ define o ID ANTES de qualquer validação que o usa
        service.setId(id);

        if(!serviceGateway.existsById(service.getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(service.getId())
            );
        }

        fileUploadGateway.uploadFile(originalFileName, imageContent);

        service.setCreatedAt(serviceGateway.findById(id).get().getCreatedAt());
        service.setUpdatedAt(LocalDateTime.now());
        service.setImage(originalFileName);

        return serviceGateway.save(service);
    }

}
