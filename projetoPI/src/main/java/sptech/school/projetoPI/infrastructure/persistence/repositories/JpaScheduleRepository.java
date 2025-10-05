package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaScheduleRepository extends JpaRepository<ScheduleJpaEntity, Integer> {
    boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime);
    boolean existsByPaymentTypeId(Integer id);
    boolean existsByEmployeeId(Integer id);
    boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime);
    boolean existsByClientId(Integer clientId);
    boolean existsByIdAndStatus(Integer id, Status status);
    List<ScheduleJpaEntity> findAllByPaymentTypeId(Integer id);
    List<ScheduleJpaEntity> findAllByEmployeeId(Integer id);
    Page<ScheduleJpaEntity> findAllByClientId(Pageable pageable, Integer clientId);
}
