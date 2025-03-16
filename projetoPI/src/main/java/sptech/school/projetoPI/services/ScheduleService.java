package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final FeedbackRepository feedbackRepository;

    public ScheduleService(ScheduleRepository repository, FeedbackRepository feedbackRepository) {
        this.repository = repository;
        this.feedbackRepository = feedbackRepository;
    }

    public Schedule signSchedule(Schedule schedule) {
        if (repository.existsByDateTime(schedule.getDateTime())) {
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
                        "O agendamento com o ID %d não foi encontrado.".formatted(id)
                )
        );
    }

    public Schedule updateScheduleById(Schedule schedule, Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado.".formatted(id)
            );
        }

        schedule.setId(id);
        return repository.save(schedule);
    }

    public ResponseEntity<Void> deleteScheduleById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado.".formatted(id)
            );
        }

        Optional<Feedback> feedback = feedbackRepository.findByScheduleId(id);

        //feedback.ifPresent(feedbackRepository::delete);

        if(feedback.isPresent()) {
            feedbackRepository.deleteById(feedback.get().getId());
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
