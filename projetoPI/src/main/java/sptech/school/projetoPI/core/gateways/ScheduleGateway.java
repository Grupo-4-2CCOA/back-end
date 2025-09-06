package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleJpaEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleGateway {
    Schedule save(Schedule schedule);
    boolean existsById(Integer id);
    boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime);
    boolean existsByPaymentTypeId(Integer id);
    boolean existsByEmployeeId(Integer id);
    boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime);
    boolean existsByClientId(Integer clientId);
    boolean existsByIdAndStatus(Integer id, Status status);
    Optional<ScheduleJpaEntity> findById(Integer id);
    List<ScheduleJpaEntity> findAll();
    List<Schedule> findAllByPaymentTypeId(Integer id);
    List<Schedule> findAllByEmployeeId(Integer id);
    List<Schedule> findAllByClientId(Integer clientId);
}
