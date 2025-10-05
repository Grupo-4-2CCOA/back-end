package sptech.school.projetoPI.core.gateways;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;

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
    Page<ScheduleDomain> findAll(Pageable pageable);
    List<ScheduleDomain> findAllByPaymentTypeId(Integer id);
    List<ScheduleDomain> findAllByEmployeeId(Integer id);
    List<ScheduleDomain> findAllByClientId(Integer clientId);
}
