package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaFeedbackRepository extends JpaRepository<FeedbackJpaEntity, Integer> {
    boolean existsById(Integer id);
    void deleteById(Integer id);
    Optional<FeedbackJpaEntity> findById(Integer id);
    Page<FeedbackJpaEntity> findAll(Pageable pageable);

    // Queries com filtro por RATING
    Page<FeedbackJpaEntity> findAllByRating(Integer rating, Pageable pageable);

    // Query com filtro por serviço (via schedule items)
    @Query("SELECT f FROM FeedbackJpaEntity f JOIN f.schedule s JOIN s.items i WHERE i.service.id = :serviceId")
    Page<FeedbackJpaEntity> findAllByServiceId(@Param("serviceId") Integer serviceId, Pageable pageable);

    // Query com filtro por funcionário
    @Query("SELECT f FROM FeedbackJpaEntity f WHERE f.schedule.employee.id = :employeeId")
    Page<FeedbackJpaEntity> findAllByEmployeeId(@Param("employeeId") Integer employeeId, Pageable pageable);

    // Query com filtros combinados
    @Query("SELECT f FROM FeedbackJpaEntity f WHERE " +
           "(:rating IS NULL OR f.rating = :rating) AND " +
           "(:employeeId IS NULL OR f.schedule.employee.id = :employeeId)")
    Page<FeedbackJpaEntity> findAllWithFilters(
            @Param("rating") Integer rating,
            @Param("employeeId") Integer employeeId,
            Pageable pageable);
}
