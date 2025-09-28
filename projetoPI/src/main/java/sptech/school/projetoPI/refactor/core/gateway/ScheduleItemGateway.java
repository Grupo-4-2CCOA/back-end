package sptech.school.projetoPI.refactor.core.gateway;

import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleItemDomain;

import java.util.List;
import java.util.Optional;

public interface ScheduleItemGateway {
    ScheduleItemDomain save(ScheduleItemDomain scheduleItemDomain);
    boolean existsById(Integer id);
    Optional<ScheduleItemDomain> findById(Integer id);
    List<ScheduleItemDomain> findAll();
    ScheduleItemDomain deleteById(Integer id);
    List<ScheduleItemDomain> findAllBySchedule_Id(Integer scheduleId);
}
