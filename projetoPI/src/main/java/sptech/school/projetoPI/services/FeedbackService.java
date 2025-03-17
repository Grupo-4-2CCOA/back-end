package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.ScheduleAlreadyRated;
import sptech.school.projetoPI.repositories.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback signFeedback(Feedback feedback) {
        if(repository.existsByScheduleId(feedback.getSchedule().getId())) {
            throw new ScheduleAlreadyRated("O Agendamento de ID %d já foi avaliado".formatted(feedback.getSchedule().getId()));
        }

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
        return null;
    }

    public ResponseEntity<Void> deleteFeedbackById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o id %d não foi encontrado.".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
