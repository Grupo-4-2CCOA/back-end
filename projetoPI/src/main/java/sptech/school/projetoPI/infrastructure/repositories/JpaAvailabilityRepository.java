package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.infrastructure.persistence.AvailabilityJpaEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaAvailabilityRepository extends JpaRepository<AvailabilityJpaEntity, Integer> {
    boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId);
    boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId);
    boolean existsByEmployeeId(Integer id);
    List<AvailabilityJpaEntity> findAllByEmployeeId(Integer employeeId);
}

