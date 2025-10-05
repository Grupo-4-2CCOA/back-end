//package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import sptech.school.projetoPI.old.core.enums.Status;
//import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.ScheduleJpaEntity;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public interface ScheduleRepository extends JpaRepository<ScheduleJpaEntity, Integer> {
//    boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime);
//    boolean existsByPaymentTypeId(Integer id);
//    boolean existsByEmployeeId(Integer id);
//    boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime);
//    boolean existsByClientId(Integer clientId);
//    boolean existsByIdAndStatus(Integer id, Status status);
//    List<ScheduleJpaEntity> findAllByPaymentTypeId(Integer id);
//    List<ScheduleJpaEntity> findAllByEmployeeId(Integer id);
//    List<ScheduleJpaEntity> findAllByClientId(Integer clientId);
//}
