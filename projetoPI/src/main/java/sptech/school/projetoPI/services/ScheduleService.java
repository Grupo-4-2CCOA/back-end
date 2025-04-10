package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.util.List;

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
        validateRequestBody(schedule);
        schedule.setId(null);
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

        validateRequestBody(schedule);

        schedule.setId(id);
        return repository.save(schedule);
    }

    public ResponseEntity<Void> deleteScheduleById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    // Validação do POST & PUT
    public void validateRequestBody(Schedule schedule) {
        if (repository.existsByDateTime(schedule.getDateTime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        if (!userRepository.existsById(schedule.getUser().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(schedule.getUser().getId())
            );
        }

        if (!paymentTypeRepository.existsById(schedule.getPaymentType().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(schedule.getPaymentType().getId())
            );
        }

        // TODO: Adicionar um if que checa se a duração do atendimento não conflita com outro marcado para o mesmo dia e que joga a TimeConflictException
    }
}
