package sptech.school.projetoPI.core.application.usecases.availability;

import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

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
