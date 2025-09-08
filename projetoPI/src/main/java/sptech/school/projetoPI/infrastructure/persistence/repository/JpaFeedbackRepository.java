package sptech.school.projetoPI.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaFeedbackRepository extends JpaRepository<FeedbackJpaEntity, Integer> {
    boolean existsByClientId(Integer clientId);
    List<FeedbackJpaEntity> findAllByClientId(Integer clientId);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    Optional<FeedbackJpaEntity> findById(Integer id);
    List<FeedbackJpaEntity> findAll();
}
