package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;

import java.time.LocalTime;
import java.util.List;

public class GetAllAvailabilityUseCase {
    private final AvailabilityGateway availabilityGateway;

    public GetAllAvailabilityUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public List<Availability> execute() {
        return availabilityGateway.findAll();
    }
}
