package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Schedule;

import java.sql.Time;
import java.time.LocalDate;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    boolean existsByDia(LocalDate dia);
    boolean existsByHora(Time hora);

}
