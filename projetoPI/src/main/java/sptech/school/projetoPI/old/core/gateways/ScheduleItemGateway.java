package sptech.school.projetoPI.old.core.gateways;

import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;

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
