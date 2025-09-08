package sptech.school.projetoPI.application.usecases.service;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

public class DeleteServiceByIdUseCase {

    private final ServiceGateway serviceGateway;

    public DeleteServiceByIdUseCase(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    public void execute(Integer id) {
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

        ServiceDomain service = serviceGateway.findById(id).get();
        service.setActive(false);
        serviceGateway.save(service);
    }

}
