package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public Feedback signFeedback(Feedback feedback) {
        validateRequestBody(feedback);
        feedback.setId(null);
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());
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
        feedback.setCreatedAt(repository.findById(id).get().getCreatedAt());
        feedback.setUpdatedAt(LocalDateTime.now());
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

    // Validação do POST & PUT
    private void validateRequestBody(Feedback feedback) {
        if (!scheduleRepository.existsById(feedback.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedback.getSchedule().getId())
            );
        }

        if (!userRepository.existsByIdAndActiveTrue(feedback.getUser().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(feedback.getUser().getId())
            );
        }
    }
}
