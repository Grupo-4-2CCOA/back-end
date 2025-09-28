package sptech.school.projetoPI.old.core.gateways;

import sptech.school.projetoPI.old.core.domains.FeedbackDomain;

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
