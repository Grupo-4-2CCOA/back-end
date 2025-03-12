package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule signSchedule(Schedule schedule) {
        if (repository.existsByDia(schedule.getDia()) && repository.existsByHora(schedule.getHora())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe."
            );
        }

        // TODO: Adicionar um if que checa se a duração do atendimento não conflita com outro marcado para o mesmo dia e que joga a TimeConflictException

        return repository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return repository.findAll();
    }

    public Schedule getScheduleById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O agendamento com o id %d não foi encontrado.".formatted(id)
                )
        );
    }

    public Schedule updateScheduleById(Schedule schedule, Integer id) {
        return null;
    }

    public ResponseEntity<Void> deleteScheduleById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o id %d não foi encontrado.".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
