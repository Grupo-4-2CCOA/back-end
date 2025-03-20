package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final AppointmentRepository appointmentRepository;

    public ScheduleService(ScheduleRepository repository, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.appointmentRepository = appointmentRepository;
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

        if (!appointmentRepository.existsById(schedule.getAppointment().getId())) {
            throw new EntityNotFoundException(
                    "O atendimento com o ID %d não foi encontrado".formatted(schedule.getAppointment().getId())
            );
        }

        // TODO: Adicionar um if que checa se a duração do atendimento não conflita com outro marcado para o mesmo dia e que joga a TimeConflictException
    }
}
