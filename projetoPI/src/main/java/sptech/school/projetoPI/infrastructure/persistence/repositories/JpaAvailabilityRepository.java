package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.AvailabilityJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface JpaAvailabilityRepository extends JpaRepository<AvailabilityJpaEntity, Integer> {
    boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId);
    boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId);
    boolean existsByEmployeeId(Integer id);
    List<AvailabilityJpaEntity> findAllByEmployeeId(Integer employeeId);
}

