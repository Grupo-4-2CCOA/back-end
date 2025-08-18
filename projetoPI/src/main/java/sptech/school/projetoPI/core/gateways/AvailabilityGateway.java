package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Availability;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityGateway {
    Availability save(Availability availability);
    boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId);
    boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId);
    boolean existsByEmployeeId(Integer id);
    boolean existsById(Integer id);
    List<Availability> findAll();
    Optional<Availability> findById(Integer id);
    Availability deleteById(Integer id);
    List<Availability> findAllByEmployeeId(Integer employeeId);
}
