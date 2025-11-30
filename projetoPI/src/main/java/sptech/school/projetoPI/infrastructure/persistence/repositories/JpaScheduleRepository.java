package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    Page<ScheduleJpaEntity> findAllByClientId(Integer clientId, Pageable pageable);

    @EntityGraph(attributePaths = { "items", "items.service" })
    Page<ScheduleJpaEntity> findAllByClient_Id(Integer clientId, Pageable pageable);

    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.appointmentDatetime BETWEEN :startDate AND :endDate")
    Page<ScheduleJpaEntity> findAllByAppointmentDatetimeBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.appointmentDatetime >= :startDate")
    Page<ScheduleJpaEntity> findAllByAppointmentDatetimeGreaterThanEqual(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.appointmentDatetime <= :endDate")
    Page<ScheduleJpaEntity> findAllByAppointmentDatetimeLessThanEqual(@Param("endDate") LocalDateTime endDate, Pageable pageable);

    @EntityGraph(attributePaths = { "items", "items.service" })
    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.client.id = :clientId AND s.appointmentDatetime BETWEEN :startDate AND :endDate")
    Page<ScheduleJpaEntity> findAllByClient_IdAndAppointmentDatetimeBetween(@Param("clientId") Integer clientId,
            @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @EntityGraph(attributePaths = { "items", "items.service" })
    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.client.id = :clientId AND s.appointmentDatetime >= :startDate")
    Page<ScheduleJpaEntity> findAllByClient_IdAndAppointmentDatetimeGreaterThanEqual(@Param("clientId") Integer clientId,
            @Param("startDate") LocalDateTime startDate, Pageable pageable);

    @EntityGraph(attributePaths = { "items", "items.service" })
    @Query("SELECT s FROM ScheduleJpaEntity s WHERE s.client.id = :clientId AND s.appointmentDatetime <= :endDate")
    Page<ScheduleJpaEntity> findAllByClient_IdAndAppointmentDatetimeLessThanEqual(@Param("clientId") Integer clientId,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
