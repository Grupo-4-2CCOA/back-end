package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleItemJpaEntity;

public interface JpaScheduleItemRepository extends JpaRepository<ScheduleItemJpaEntity, Integer> {
}
