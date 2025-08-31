package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.mappers.AvailabilityMapper;
import sptech.school.projetoPI.infrastructure.repositories.JpaAvailabilityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AvailabilityRepositoryImpl implements AvailabilityGateway {

    private final JpaAvailabilityRepository jpaRepository;

    @Override
    public Availability save(Availability availability) {
        var entity = AvailabilityMapper.toJpaEntity(availability);
        var saved = jpaRepository.save(entity);
        return AvailabilityMapper.toDomain(saved);
    }

    @Override
    public boolean existsByDayAndEmployeeId(LocalDate day, Integer employeeId) {
        return jpaRepository.existsByDayAndEmployeeId(day, employeeId);
    }

    @Override
    public boolean existsByIdNotAndDayAndEmployeeId(Integer id, LocalDate day, Integer employeeId) {
        return jpaRepository.existsByIdNotAndDayAndEmployeeId(id, day, employeeId);
    }

    @Override
    public boolean existsByEmployeeId(Integer id) {
        return jpaRepository.existsByEmployeeId(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Availability> findAll() {
        return jpaRepository.findAll().stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Availability> findById(Integer id) {
        return jpaRepository.findById(id).map(AvailabilityMapper::toDomain);
    }

    @Override
    public Availability deleteById(Integer id) {
        Optional<Availability> availabilityOpt = findById(id);
        availabilityOpt.ifPresent(a -> jpaRepository.deleteById(id));
        return availabilityOpt.orElse(null);
    }

    @Override
    public List<Availability> findAllByEmployeeId(Integer employeeId) {
        return jpaRepository.findAllByEmployeeId(employeeId).stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toList());
    }
}

