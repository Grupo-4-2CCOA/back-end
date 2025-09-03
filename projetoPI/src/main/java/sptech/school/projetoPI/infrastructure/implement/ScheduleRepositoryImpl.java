package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.infrastructure.mappers.ScheduleMapper;
import sptech.school.projetoPI.infrastructure.repositories.JpaScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleGateway {

    private final JpaScheduleRepository repository;

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
    public List<Schedule> findAllByPaymentTypeId(Integer id) {
        return repository.findAllByPaymentTypeId(id);
    }

    @Override
    public List<Schedule> findAllByEmployeeId(Integer id) {
        return repository.findAllByEmployeeId(id);
    }

    @Override
    public List<Schedule> findAllByClientId(Integer clientId) {
        return repository.findAllByClientId(clientId);
    }
}
