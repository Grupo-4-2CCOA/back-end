package sptech.school.projetoPI.refactor.core.gateway;

import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;
import sptech.school.projetoPI.refactor.core.domain.enumerator.ScheduleStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleGateway {
    ScheduleDomain save(ScheduleDomain scheduleDomain);
    boolean existsById(Integer id);
    boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime);
    boolean existsByPaymentTypeId(Integer id);
    boolean existsByEmployeeId(Integer id);
    boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime);
    boolean existsByClientId(Integer clientId);
    boolean existsByIdAndStatus(Integer id, ScheduleStatus status);
    Optional<ScheduleDomain> findById(Integer id);
    List<ScheduleDomain> findAll();
    List<ScheduleDomain> findAllByPaymentTypeId(Integer id);
    List<ScheduleDomain> findAllByEmployeeId(Integer id);
    List<ScheduleDomain> findAllByClientId(Integer clientId);
}
