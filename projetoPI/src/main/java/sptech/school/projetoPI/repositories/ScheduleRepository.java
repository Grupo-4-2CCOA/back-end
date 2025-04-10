package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    boolean existsByDateTime(LocalDateTime dateTime);
    boolean existsByPaymentTypeId(Integer id);
    List<Schedule> findAllByPaymentTypeId(Integer id);
    boolean existsByUserId(Integer id);
    List<Schedule> findAllByUserId(Integer id);
}
