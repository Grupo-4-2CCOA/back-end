package sptech.school.projetoPI.old.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.enums.Status;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.old.infrastructure.mappers.ScheduleMapper;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.ScheduleJpaEntity;
import sptech.school.projetoPI.old.infrastructure.persistence.repositories.JpaScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleAdapter implements ScheduleGateway {

    private final JpaScheduleRepository repository;

    @Override
    public ScheduleDomain save(ScheduleDomain scheduleDomain) {
        ScheduleJpaEntity jpaEntity = ScheduleMapper.toJpaEntity(scheduleDomain);
        ScheduleJpaEntity savedEntity = repository.save(jpaEntity);
        return ScheduleMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByAppointmentDatetime(LocalDateTime appointmentDatetime) {
        return repository.existsByAppointmentDatetime(appointmentDatetime);
    }

    @Override
    public boolean existsByPaymentTypeId(Integer id) {
        return repository.existsByPaymentTypeId(id);
    }

    @Override
    public boolean existsByEmployeeId(Integer id) {
        return repository.existsByEmployeeId(id);
    }

    @Override
    public boolean existsByIdNotAndAppointmentDatetime(Integer id, LocalDateTime appointmentDatetime) {
        return repository.existsByIdNotAndAppointmentDatetime(id, appointmentDatetime);
    }

    @Override
    public boolean existsByClientId(Integer clientId) {
        return repository.existsByClientId(clientId);
    }

    @Override
    public boolean existsByIdAndStatus(Integer id, Status status) {
        return repository.existsByIdAndStatus(id, status);
    }

    @Override
    public Optional<ScheduleDomain> findById(Integer id) {
        return repository.findById(id).map(ScheduleMapper::toDomain);
    }

    @Override
    public List<ScheduleDomain> findAll() {
        return repository.findAll().stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDomain> findAllByPaymentTypeId(Integer id) {
        return repository.findAllByPaymentTypeId(id).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDomain> findAllByEmployeeId(Integer id) {
        return repository.findAllByEmployeeId(id).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDomain> findAllByClientId(Integer clientId) {
        return repository.findAllByClientId(clientId).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }
}