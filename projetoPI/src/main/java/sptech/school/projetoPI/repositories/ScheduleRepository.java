package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Schedule;

import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    boolean existsByDateTime(LocalDateTime dateTime);
    boolean existsByEmployeeId(Integer employeeId);
    boolean existsByServiceId(Integer serviceId);
    boolean existsByUserId(Integer userId);
}
