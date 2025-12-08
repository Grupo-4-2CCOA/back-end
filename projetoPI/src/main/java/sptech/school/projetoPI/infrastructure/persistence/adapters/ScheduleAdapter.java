package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.infrastructure.mappers.ScheduleMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleAdapter implements ScheduleGateway {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleAdapter.class);
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
    public Page<ScheduleDomain> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        return findAll(pageable, startDate, endDate, null);
    }

    @Override
    public Page<ScheduleDomain> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate, Status status) {
        logger.info("findAll - startDate: {}, endDate: {}, status: {}", startDate, endDate, status);
        
        // Com filtro de status
        if (status != null) {
            if (startDate != null && endDate != null) {
                logger.info("Applying status + BETWEEN filter");
                return repository.findAllByStatusAndAppointmentDatetimeBetween(status, startDate, endDate, pageable)
                        .map(ScheduleMapper::toDomain);
            } else if (startDate != null) {
                logger.info("Applying status + >= filter");
                return repository.findAllByStatusAndAppointmentDatetimeGreaterThanEqual(status, startDate, pageable)
                        .map(ScheduleMapper::toDomain);
            } else if (endDate != null) {
                logger.info("Applying status + <= filter");
                return repository.findAllByStatusAndAppointmentDatetimeLessThanEqual(status, endDate, pageable)
                        .map(ScheduleMapper::toDomain);
            }
            logger.info("Applying status only filter");
            return repository.findAllByStatus(status, pageable).map(ScheduleMapper::toDomain);
        }
        
        // Sem filtro de status
        if (startDate != null && endDate != null) {
            logger.info("Applying BETWEEN filter: {} to {}", startDate, endDate);
            return repository.findAllByAppointmentDatetimeBetween(startDate, endDate, pageable)
                    .map(ScheduleMapper::toDomain);
        } else if (startDate != null) {
            logger.info("Applying >= filter: {}", startDate);
            return repository.findAllByAppointmentDatetimeGreaterThanEqual(startDate, pageable)
                    .map(ScheduleMapper::toDomain);
        } else if (endDate != null) {
            logger.info("Applying <= filter: {}", endDate);
            return repository.findAllByAppointmentDatetimeLessThanEqual(endDate, pageable)
                    .map(ScheduleMapper::toDomain);
        }
        logger.info("No filter applied");
        return repository.findAll(pageable).map(ScheduleMapper::toDomain);
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
    public Page<ScheduleDomain> findAllByClientId(Integer clientId, Pageable pageable, LocalDateTime startDate,
            LocalDateTime endDate) {
        logger.info("findAllByClientId - clientId: {}, startDate: {}, endDate: {}", clientId, startDate, endDate);
        if (startDate != null && endDate != null) {
            logger.info("Applying BETWEEN filter: {} to {}", startDate, endDate);
            return repository.findAllByClient_IdAndAppointmentDatetimeBetween(clientId, startDate, endDate, pageable)
                    .map(ScheduleMapper::toDomain);
        } else if (startDate != null) {
            logger.info("Applying >= filter: {}", startDate);
            return repository.findAllByClient_IdAndAppointmentDatetimeGreaterThanEqual(clientId, startDate, pageable)
                    .map(ScheduleMapper::toDomain);
        } else if (endDate != null) {
            logger.info("Applying <= filter: {}", endDate);
            return repository.findAllByClient_IdAndAppointmentDatetimeLessThanEqual(clientId, endDate, pageable)
                    .map(ScheduleMapper::toDomain);
        }
        logger.info("No date filter applied");
        return repository.findAllByClient_Id(clientId, pageable).map(ScheduleMapper::toDomain);
    }
}