package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Feedback;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    boolean existsByScheduleId(Integer scheduleId);
    boolean existsByIdNotAndScheduleId(Integer id, Integer scheduleId);
    Optional<Feedback> findByScheduleId(Integer scheduleId);
}
