package sptech.school.projetoPI.core.application.usecases.availability;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;

import java.time.LocalDateTime;

public class CreateAvailabilityUseCase {

    private final AvailabilityGateway availabilityGateway;

    public CreateAvailabilityUseCase(AvailabilityGateway availabilityGateway)  {
        this.availabilityGateway = availabilityGateway;
    }

    public AvailabilityDomain execute(AvailabilityDomain availabilityDomain) {
        if(availabilityDomain.getStartTime().isAfter(availabilityDomain.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availabilityDomain.getStartTime(), availabilityDomain.getEndTime())
            );
        }

        if(!availabilityGateway.existsById(availabilityDomain.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "Employee com o ID %d não foi encontrado".formatted(availabilityDomain.getEmployee().getId())
            );
        }

        if(availabilityGateway.existsByDayAndEmployeeId(availabilityDomain.getDay(), availabilityDomain.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availabilityDomain.getDay())
            );
        }

        availabilityDomain.setId(null);
        availabilityDomain.setCreatedAt(LocalDateTime.now());
        availabilityDomain.setUpdatedAt(LocalDateTime.now());
        return availabilityGateway.save(availabilityDomain);
    }
}
