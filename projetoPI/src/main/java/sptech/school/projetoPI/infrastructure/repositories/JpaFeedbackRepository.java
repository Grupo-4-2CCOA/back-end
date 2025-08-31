package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.infrastructure.persistence.FeedbackJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaFeedbackRepository extends JpaRepository<FeedbackJpaEntity, Integer> {
    boolean existsByClientId(Integer clientId);
    List<Feedback> findAllByClientId(Integer clientId);
    Feedback save(Feedback feedback);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    Optional<FeedbackJpaEntity> findById(Integer id);
    List<FeedbackJpaEntity> findAll();
}
