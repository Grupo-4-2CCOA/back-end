package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleGateway {
    boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime);
    boolean existsByPaymentTypeId(Integer id);
    boolean existsByEmployeeId(Integer id);
    boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime);
    boolean existsByClientId(Integer clientId);
    boolean existsByIdAndStatus(Integer id, Status status);
    List<Schedule> findAllByPaymentTypeId(Integer id);
    List<Schedule> findAllByEmployeeId(Integer id);
    List<Schedule> findAllByClientId(Integer clientId);
}
