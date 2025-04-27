package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Feedback;

import java.util.Arrays;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    boolean existsByClientId(Integer clientId);
    List<Feedback> findAllByClientId(Integer clientId);

    boolean existsByUserId(Integer id);

    Arrays findAllByUserId(Integer id);
}
