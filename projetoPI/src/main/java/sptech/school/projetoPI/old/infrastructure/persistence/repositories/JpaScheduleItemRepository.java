package sptech.school.projetoPI.old.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.ScheduleItemJpaEntity;

import java.util.List;

public interface JpaScheduleItemRepository extends JpaRepository<ScheduleItemJpaEntity, Integer> {
    List<ScheduleItemJpaEntity> findAllBySchedule_Id(Integer scheduleId);
}
