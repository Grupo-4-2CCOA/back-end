package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.ScheduleAlreadyRated;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.ArrayList;
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
        if(repository.existsById(feedback.getId())) {
            throw new ScheduleAlreadyRated(
                    "Você já avaliou este agendamento antes"
            );
        }

//        validateRequestBody(feedback);
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

        if(repository.existsById(feedback.getId())) {
            throw new ScheduleAlreadyRated(
                    "Você já avaliou este serviço antes"
            );
        }

//        validateRequestBody(feedback);
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

    // Validação do POST & PUT
//    public void validateRequestBody(Feedback feedback) {
//        List<String> entidadesNaoEncontradas = new ArrayList<>();
//
//        if(!userRepository.existsById(feedback.getUser().getId())) entidadesNaoEncontradas.add("Usuário");
//        if(!scheduleRepository.existsById(feedback.getSchedule().getId())) entidadesNaoEncontradas.add("Agendamento");
//
//        if(!entidadesNaoEncontradas.isEmpty()) {
//            throw new EntityNotFoundException(
//                    "As seguintes entidades não foram encontrada: " + entidadesNaoEncontradas
//            );
//        }
//    }
}
