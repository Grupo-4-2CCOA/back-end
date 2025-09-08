package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleItemJpaEntity;

public interface JpaScheduleItemRepository extends JpaRepository<ScheduleItemJpaEntity, Integer> {

}
