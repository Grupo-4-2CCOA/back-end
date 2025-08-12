package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.InvalidTimeRangeException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.AvailabilityRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvailabilityService extends AbstractService<Availability> {
    private final AvailabilityRepository repository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Availability postMethod(Availability availability) {
        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            log.error("{} - [ERROR 400] POST: Horário inicial é posterior ao horário final", LocalTime.now());
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody(availability);

        if(repository.existsByDayAndEmployeeId(availability.getDay(), availability.getEmployee().getId())) {
            log.error("{} - [ERROR 409] POST: Horário já cadastrado", LocalTime.now());
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(null);
        availability.setCreatedAt(LocalDateTime.now());
        availability.setUpdatedAt(LocalDateTime.now());
        log.info("{} - [INFO] POST: Entidade cadastrada", LocalTime.now());
        return repository.save(availability);
    }

    @Override
    public List<Availability> getAllMethod() {
        log.info("{} - [INFO] GET: Retornando lista de entidades", LocalTime.now());
        return repository.findAll();
    }

    @Override
    public Availability getByIdMethod(Integer id) {
        return repository.findById(id)
                .map((entity) -> {
                    log.info("{} - [INFO] GET: Retornando entidade de ID {}", LocalTime.now(), id);
                    return entity;
                })
                .orElseThrow(() -> {
                    log.error("{} - [ERROR 404] GET: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
                    return new EntityNotFoundException(
                            "Availability de ID %d não foi encontrado".formatted(id)
                    );
                });
    }

    @Override
    public Availability putByIdMethod(Availability availability, Integer id) {
        if (!repository.existsById(id)) {
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

        validateRequestBody(availability);

        if(repository.existsByIdNotAndDayAndEmployeeId(availability.getId(), availability.getDay(), availability.getEmployee().getId())) {
            log.error("{} - [ERROR 409] PUT: Horário já cadastrado", LocalTime.now());
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(id);
        availability.setCreatedAt(repository.findById(id).get().getCreatedAt());
        availability.setUpdatedAt(LocalDateTime.now());
        log.info("{} - [INFO] PUT: Entidade de ID {} atualizada", LocalTime.now(), id);
        return repository.save(availability);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            log.error("{} - [ERROR 404] DELETE: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
        log.info("{} - [INFO] DELETE: Entidade de ID {} inativada", LocalTime.now(), id);
    }

    private void validateRequestBody(Availability availability) {
        if(!employeeRepository.existsById(availability.getEmployee().getId())) {
            log.error("{} - [ERROR 404] REQUEST BODY VALIDATION: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), availability.getEmployee().getId());
            throw new RelatedEntityNotFoundException(
                    "Employee com o ID %d não foi encontrado".formatted(availability.getEmployee().getId())
            );
        }
    }
}
