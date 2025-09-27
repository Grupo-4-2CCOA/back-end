package sptech.school.projetoPI.old.core.application.usecases.availability;

import sptech.school.projetoPI.old.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.old.core.gateways.AvailabilityGateway;

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
