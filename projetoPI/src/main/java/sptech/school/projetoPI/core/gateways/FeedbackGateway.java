package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackGateway {
    Feedback save(Feedback feedback);
    boolean existsById(Integer id);
    Feedback deleteById(Integer id);
    boolean existsByClientId(Integer clientId);
    List<Feedback> findAllByClientId(Integer clientId);
    Optional<Feedback> findById(Integer id);
    List<Feedback> findAll();
}
