package sptech.school.projetoPI.application.usecases.availability;

import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;

import java.time.LocalDateTime;

public class UpdateAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;
    private final ValidateRequestBodyUseCase validateRequestBody;

    public UpdateAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway, ValidateRequestBodyUseCase validateRequestBody) {
        this.availabilityGateway = availabilityGateway;
        this.validateRequestBody = validateRequestBody;
    }

    public Availability execute(Integer id, Availability availability) {
        if (!availabilityGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody.execute(availability);

        if(availabilityGateway.existsByIdNotAndDayAndEmployeeId(availability.getId(), availability.getDay(), availability.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(id);
        availability.setCreatedAt(availabilityGateway.findById(id).get().getCreatedAt());
        availability.setUpdatedAt(LocalDateTime.now());
        return availabilityGateway.save(availability);
    }
}
