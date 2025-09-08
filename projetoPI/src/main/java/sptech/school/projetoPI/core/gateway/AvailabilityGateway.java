package sptech.school.projetoPI.core.gateway;

import sptech.school.projetoPI.core.domain.AvailabilityDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityGateway {
    AvailabilityDomain save(AvailabilityDomain availabilityDomain);
    boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId);
    boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId);
    boolean existsByEmployeeId(Integer id);
    boolean existsById(Integer id);
    List<AvailabilityDomain> findAll();
    Optional<AvailabilityDomain> findById(Integer id);
    List<AvailabilityDomain> findAllByEmployeeId(Integer employeeId);
    AvailabilityDomain deleteById(Integer id);
}
