package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.mappers.AvailabilityMapper;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaAvailabilityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityAdapter implements AvailabilityGateway {

    private final JpaAvailabilityRepository jpaRepository;

    @Override
    public AvailabilityDomain save(AvailabilityDomain availabilityDomain) {
        var entity = AvailabilityMapper.toJpaEntity(availabilityDomain);
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
    public List<AvailabilityDomain> findAll() {
        return jpaRepository.findAll().stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AvailabilityDomain> findById(Integer id) {
        return jpaRepository.findById(id).map(AvailabilityMapper::toDomain);
    }

    @Override
    public AvailabilityDomain deleteById(Integer id) {
        Optional<AvailabilityDomain> availabilityOpt = findById(id);
        availabilityOpt.ifPresent(a -> jpaRepository.deleteById(id));
        return availabilityOpt.orElse(null);
    }

    @Override
    public List<AvailabilityDomain> findAllByEmployeeId(Integer employeeId) {
        return jpaRepository.findAllByEmployeeId(employeeId).stream()
                .map(AvailabilityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
