package sptech.school.projetoPI.application.usecases.availability;

import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

import java.time.LocalTime;

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
