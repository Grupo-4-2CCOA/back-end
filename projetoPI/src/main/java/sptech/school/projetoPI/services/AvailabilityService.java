package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.InvalidTimeRangeException;
import sptech.school.projetoPI.repositories.AvailabilityRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository repository;
    private final EmployeeRepository employeeRepository;

    public Availability signAvailability(Availability availability) {
        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody(availability);

        if(repository.existsByDayAndEmployeeId(availability.getDay(), availability.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(null);
        availability.setCreatedAt(LocalDateTime.now());
        availability.setUpdatedAt(LocalDateTime.now());
        return repository.save(availability);
    }

    public List<Availability> getAllAvailabilities() {
        return repository.findAll();
    }

    public Availability getAvailabilityById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O dia de disponibilidade com o ID %d não foi encontrada".formatted(id)
                )
        );
    }

    public Availability updateAvailabilityById(Availability availability, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O dia de disponibilidade com o ID %d não foi encontrada".formatted(id)
            );
        }

        if(availability.getStartTime().isAfter(availability.getEndTime())) {
            throw new InvalidTimeRangeException(
                    "O horário inicial %s é posterior ao horário final %s".formatted(availability.getStartTime(), availability.getEndTime())
            );
        }

        validateRequestBody(availability);

        if(repository.existsByIdNotAndDayAndEmployeeId(availability.getId(), availability.getDay(), availability.getEmployee().getId())) {
            throw new EntityConflictException(
                    "Já existe um horário cadastrado para este funcionário no dia %s".formatted(availability.getDay())
            );
        }

        availability.setId(id);
        availability.setCreatedAt(repository.findById(id).get().getCreatedAt());
        availability.setUpdatedAt(LocalDateTime.now());
        return repository.save(availability);
    }

    public void deleteAvailabilityById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O dia de disponibilidade com o ID %d não foi encontrada".formatted(id)
            );
        }

        repository.deleteById(id);
    }

    // Validação do POST & PUT
    private void validateRequestBody(Availability availability) {
        if(!employeeRepository.existsById(availability.getEmployee().getId())) {
            throw new EntityNotFoundException(
                    "O funcionário de ID %d não foi encontrado".formatted(availability.getEmployee().getId())
            );
        }
    }
}
