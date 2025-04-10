package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ScheduleAlreadyRated;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository repository, ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public Feedback signFeedback(Feedback feedback) {
        validateRequestBody(feedback);
        feedback.setId(null);
        return repository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return repository.findAll();
    }

    public Feedback getFeedbackById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O feedback com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Feedback updateFeedbackById(Feedback feedback, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        validateRequestBody(feedback);
        feedback.setId(id);
        return repository.save(feedback);
    }

    public ResponseEntity<Void> deleteFeedbackById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    public void validateRequestBody(Feedback feedback) {
        if (repository.existsById(feedback.getId())) {
            throw new ScheduleAlreadyRated(
                    "Você já avaliou este serviço antes"
            );
        }

        if (!scheduleRepository.existsById(feedback.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedback.getSchedule().getId())
            );
        }

        if (!userRepository.existsById(feedback.getUser().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(feedback.getUser().getId())
            );
        }
    }
}
