package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

import java.time.LocalTime;

public class GetAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;

    public GetAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public Availability execute(Integer id) {
        return availabilityGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Availability de ID %d não foi encontrado".formatted(id)
                ));
    }
}
