package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleItemJpaEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleItemGateway {
    ScheduleItem save(ScheduleItem scheduleItem);
    boolean existsById(Integer id);
    Optional<ScheduleItemJpaEntity> findById(Integer id);
    List<ScheduleItemJpaEntity> findAll();
}
