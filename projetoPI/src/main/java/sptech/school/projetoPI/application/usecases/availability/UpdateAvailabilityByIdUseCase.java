package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InvalidTimeRangeException;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;
    private final ValidateRequestBodyUseCase validateRequestBody;

    public Availability execute(Availability availability, Integer id) {
        if (!availabilityGateway.existsById(id)) {
            log.error("{} - [ERROR 404] PUT: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            log.error("{} - [ERROR 400] PUT: Horário inicial é posterior ao horário final", LocalTime.now());
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody.execute(availability);

        if(availabilityGateway.existsByIdNotAndDayAndEmployeeId(availability.getId(), availability.getDay(), availability.getEmployee().getId())) {
            log.error("{} - [ERROR 409] PUT: Horário já cadastrado", LocalTime.now());
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(id);
        availability.setCreatedAt(availabilityGateway.findById(id).get().getCreatedAt());
        availability.setUpdatedAt(LocalDateTime.now());
        log.info("{} - [INFO] PUT: Entidade de ID {} atualizada", LocalTime.now(), id);
        return availabilityGateway.save(availability);
    }
}
