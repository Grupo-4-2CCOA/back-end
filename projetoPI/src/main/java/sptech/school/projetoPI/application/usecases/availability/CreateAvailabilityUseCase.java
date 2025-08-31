package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateAvailabilityUseCase {

    private final AvailabilityGateway availabilityGateway;
    private final ValidateRequestBodyUseCase validateRequestBody;

    public CreateAvailabilityUseCase(AvailabilityGateway availabilityGateway, ValidateRequestBodyUseCase validateRequestBody) {
        this.availabilityGateway = availabilityGateway;
        this.validateRequestBody = validateRequestBody;
    }

    public Availability execute(Availability availability) {
        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody.execute(availability);

        if(availabilityGateway.existsByDayAndEmployeeId(availability.getDay(), availability.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(null);
        availability.setCreatedAt(LocalDateTime.now());
        availability.setUpdatedAt(LocalDateTime.now());
        return availabilityGateway.save(availability);
    }
}
