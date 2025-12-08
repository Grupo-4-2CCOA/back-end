package sptech.school.projetoPI.core.gateways;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.FeedbackDomain;

import java.util.List;
import java.util.Optional;

public interface FeedbackGateway {
    FeedbackDomain save(FeedbackDomain feedbackDomain);
    boolean existsById(Integer id);
    FeedbackDomain deleteById(Integer id);
    Optional<FeedbackDomain> findById(Integer id);
    Page<FeedbackDomain> findAll(Pageable pageable);
    Page<FeedbackDomain> findAllWithFilters(Pageable pageable, Integer rating, Integer employeeId);
}
