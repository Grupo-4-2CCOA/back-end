package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.infrastructure.mappers.ScheduleMapper;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleGateway {

    private final JpaScheduleRepository repository;

    @Override
    public Schedule save(Schedule schedule) {
        ScheduleJpaEntity jpaEntity = ScheduleMapper.toJpaEntity(schedule);
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
    public Optional<Schedule> findById(Integer id) {
        return repository.findById(id).map(ScheduleMapper::toDomain);
    }

    @Override
    public List<Schedule> findAll() {
        return repository.findAll().stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAllByPaymentTypeId(Integer id) {
        return repository.findAllByPaymentTypeId(id).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAllByEmployeeId(Integer id) {
        return repository.findAllByEmployeeId(id).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAllByClientId(Integer clientId) {
        return repository.findAllByClientId(clientId).stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }
}