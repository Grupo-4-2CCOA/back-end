package sptech.school.projetoPI.core.gateway;

import sptech.school.projetoPI.core.domain.FeedbackDomain;

import java.util.List;
import java.util.Optional;

public interface FeedbackGateway {
    FeedbackDomain save(FeedbackDomain feedbackDomain);
    boolean existsById(Integer id);
    FeedbackDomain deleteById(Integer id);
    boolean existsByClientId(Integer clientId);
    List<FeedbackDomain> findAllByClientId(Integer clientId);
    Optional<FeedbackDomain> findById(Integer id);
    List<FeedbackDomain> findAll();
}
