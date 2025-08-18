package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService extends AbstractService<Feedback> {
    private final FeedbackRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final ClientRepository clientRepository;

    @Override
    public Feedback postMethod(Feedback feedback) {
        validateRequestBody(feedback);
        feedback.setId(null);
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());
        return repository.save(feedback);
    }

    @Override
    public List<Feedback> getAllMethod() {
        return repository.findAll();
    }

    @Override
    public Feedback getByIdMethod(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O feedback com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    @Override
    public Feedback putByIdMethod(Feedback feedback, Integer id) {
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

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
    }

    private void validateRequestBody(Feedback feedback) {
        if (!scheduleRepository.existsById(feedback.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedback.getSchedule().getId())
            );
        }

        if (!clientRepository.existsByIdAndActiveTrue(feedback.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(feedback.getClient().getId())
            );
        }
    }
}
