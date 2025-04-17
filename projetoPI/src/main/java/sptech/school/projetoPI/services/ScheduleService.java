package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.enums.Status;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public ScheduleService(ScheduleRepository repository, UserRepository userRepository, PaymentTypeRepository paymentTypeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public Schedule signSchedule(Schedule schedule) {
        if (repository.existsByAppointmentDatetime(schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        validateRequestBody(schedule);
        schedule.setId(null);
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        return repository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return repository.findAll();
    }

    public Schedule getScheduleById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O agendamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Schedule updateScheduleById(Schedule schedule, Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdNotAndAppointmentDatetime(id, schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        validateRequestBody(schedule);
        schedule.setId(id);
        schedule.setCreatedAt(repository.findById(id).get().getCreatedAt());
        return repository.save(schedule);
    }

    public void deleteScheduleById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        Schedule schedule = repository.findById(id).get();
        schedule.setStatus(Status.CANCELED);
        schedule.setUpdatedAt(LocalDateTime.now());
        repository.save(schedule);
    }

    // Validação do POST & PUT
    public void validateRequestBody(Schedule schedule) {
        if (!userRepository.existsById(schedule.getUser().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(schedule.getUser().getId())
            );
        }

        if (schedule.getPaymentType() != null && !paymentTypeRepository.existsById(schedule.getPaymentType().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(schedule.getPaymentType().getId())
            );
        }

        // TODO: Adicionar um if que checa se a duração do atendimento não conflita com outro marcado para o mesmo dia e que joga a TimeConflictException
    }
}
