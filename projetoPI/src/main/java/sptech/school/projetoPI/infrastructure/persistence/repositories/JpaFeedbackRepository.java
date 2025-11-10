package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaFeedbackRepository extends JpaRepository<FeedbackJpaEntity, Integer> {
    boolean existsById(Integer id);
    void deleteById(Integer id);
    Optional<FeedbackJpaEntity> findById(Integer id);
    Page<FeedbackJpaEntity> findAll(Pageable pageable);
}
