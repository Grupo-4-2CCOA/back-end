package sptech.school.projetoPI.old.core.gateways;

import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.enums.Status;

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
    boolean existsByIdAndStatus(Integer id, Status status);
    Optional<ScheduleDomain> findById(Integer id);
    List<ScheduleDomain> findAll();
    List<ScheduleDomain> findAllByPaymentTypeId(Integer id);
    List<ScheduleDomain> findAllByEmployeeId(Integer id);
    List<ScheduleDomain> findAllByClientId(Integer clientId);
}
