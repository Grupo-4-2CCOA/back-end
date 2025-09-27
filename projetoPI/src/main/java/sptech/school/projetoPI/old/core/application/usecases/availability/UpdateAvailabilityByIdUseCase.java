package sptech.school.projetoPI.old.core.application.usecases.availability;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.old.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;

import java.time.LocalDateTime;

public class UpdateAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;

    public UpdateAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        this.availabilityGateway = availabilityGateway;
    }

    public AvailabilityDomain execute(Integer id, AvailabilityDomain availabilityDomain) {
        if (!availabilityGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(!availabilityGateway.existsById(availabilityDomain.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "Employee com o ID %d não foi encontrado".formatted(availabilityDomain.getEmployee().getId())
            );
        }

        if(availabilityDomain.getStartTime().isAfter(availabilityDomain.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availabilityDomain.getStartTime(), availabilityDomain.getEndTime())
            );
        }

        if(availabilityGateway.existsByIdNotAndDayAndEmployeeId(availabilityDomain.getId(), availabilityDomain.getDay(), availabilityDomain.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availabilityDomain.getDay())
            );
        }

        availabilityDomain.setId(id);
        availabilityDomain.setCreatedAt(availabilityGateway.findById(id).get().getCreatedAt());
        availabilityDomain.setUpdatedAt(LocalDateTime.now());
        return availabilityGateway.save(availabilityDomain);
    }
}
