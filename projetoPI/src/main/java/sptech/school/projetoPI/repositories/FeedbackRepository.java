package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
