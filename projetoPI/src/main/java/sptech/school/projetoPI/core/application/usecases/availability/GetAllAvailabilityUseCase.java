package sptech.school.projetoPI.core.application.usecases.availability;

import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;

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
