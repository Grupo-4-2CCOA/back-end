package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalTime;

public class ValidateRequestBodyUseCase {

    private final AvailabilityGateway availabilityGateway;

    public ValidateRequestBodyUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public void execute(Availability availability) {
        if(!availabilityGateway.existsById(availability.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "Employee com o ID %d não foi encontrado".formatted(availability.getEmployee().getId())
            );
        }
    }
}
