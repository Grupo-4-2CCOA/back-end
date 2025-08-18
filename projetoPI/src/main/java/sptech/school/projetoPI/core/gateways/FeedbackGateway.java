package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Feedback;

import java.util.List;

public interface FeedbackGateway {
    boolean existsByClientId(Integer clientId);
    List<Feedback> findAllByClientId(Integer clientId);
}
