package sptech.school.projetoPI.core.application.usecase.availability;

import sptech.school.projetoPI.core.domain.AvailabilityDomain;
import sptech.school.projetoPI.core.gateway.AvailabilityGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;

public class GetAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;

    public GetAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public AvailabilityDomain execute(Integer id) {
        return availabilityGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Availability de ID %d não foi encontrado".formatted(id)
                ));
    }
}
