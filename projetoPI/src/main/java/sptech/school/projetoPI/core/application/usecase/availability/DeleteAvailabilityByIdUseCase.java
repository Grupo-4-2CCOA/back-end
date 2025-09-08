package sptech.school.projetoPI.core.application.usecase.availability;

import sptech.school.projetoPI.core.gateway.AvailabilityGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;

public class DeleteAvailabilityByIdUseCase {
    private final AvailabilityGateway availabilityGateway;

    public DeleteAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public void execute(Integer id) {
        if (!availabilityGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        availabilityGateway.deleteById(id);
    }
}
