package sptech.school.projetoPI.application.usecases.availability;

import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

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
