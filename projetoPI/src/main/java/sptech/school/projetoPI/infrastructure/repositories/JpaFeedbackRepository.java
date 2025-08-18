package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.infrastructure.persistence.FeedbackJpaEntity;

import java.util.List;

public interface JpaFeedbackRepository extends JpaRepository<FeedbackJpaEntity, Integer> {
    boolean existsByClientId(Integer clientId);
    List<Feedback> findAllByClientId(Integer clientId);
}
