package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId);
    boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId);
    boolean existsByEmployeeId(Integer id);
    List<Availability> findAllByEmployeeId(Integer employeeId);
}
