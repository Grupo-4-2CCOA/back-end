package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    boolean existsByDay(LocalDate day);
    boolean existsByTime(LocalTime time);

}
