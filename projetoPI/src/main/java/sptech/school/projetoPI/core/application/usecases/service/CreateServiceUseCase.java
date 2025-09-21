package sptech.school.projetoPI.core.application.usecases.service;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class CreateServiceUseCase {

    private final ServiceGateway serviceGateway;
    private final FileUploadGateway fileUploadGateway;

    public CreateServiceUseCase(ServiceGateway serviceGateway, FileUploadGateway fileUploadGateway) {
        this.serviceGateway = serviceGateway;
        this.fileUploadGateway = fileUploadGateway;
    }

    public ServiceDomain execute(ServiceDomain service, byte[] imageContent, String originalFileName) {
        if (serviceGateway.existsByName(service.getName())) {
            throw new EntityConflictException("Este serviço já está cadastrado");
        }

        System.out.println("Categoria que será usada: " + service.getCategory().getId());

        fileUploadGateway.uploadFile(originalFileName, imageContent);

        service.setId(null);
        service.setImage(originalFileName);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());

        return serviceGateway.save(service);
    }
}
