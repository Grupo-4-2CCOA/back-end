package sptech.school.projetoPI.core.application.usecase.availability;

import sptech.school.projetoPI.core.domain.AvailabilityDomain;
import sptech.school.projetoPI.core.gateway.AvailabilityGateway;

import java.util.List;

public class GetAllAvailabilityUseCase {
    private final AvailabilityGateway availabilityGateway;

    public GetAllAvailabilityUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public List<AvailabilityDomain> execute() {
        return availabilityGateway.findAll();
    }
}
