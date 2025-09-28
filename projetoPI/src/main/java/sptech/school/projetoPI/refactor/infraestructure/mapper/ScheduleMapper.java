package sptech.school.projetoPI.refactor.infrastructure.mapper;

import sptech.school.projetoPI.old.infrastructure.mappers.ClientMapper;
import sptech.school.projetoPI.old.infrastructure.mappers.EmployeeMapper;
import sptech.school.projetoPI.old.infrastructure.mappers.PaymentTypeMapper;
import sptech.school.projetoPI.old.infrastructure.mappers.ServiceMapper;
import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;
import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleItemDomain;
import sptech.school.projetoPI.refactor.infraestructure.mapper.ScheduleItemMapper;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.ScheduleItemJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.ScheduleJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.CreateScheduleRequestDto;
import sptech.school.projetoPI.refactor.core.application.command.schedule.CreateScheduleCommand;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.CreateScheduleResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.scheduleItem.CreateScheduleItemResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class ScheduleMapper {

    public static ScheduleDomain toDomain(ScheduleJpaEntity entity) {
        if (entity == null) return null;

        return new ScheduleDomain(
                entity.getId(),
                entity.getStatus(),
                entity.getAppointmentDatetime(),
                entity.getDuration(),
                entity.getTransactionHash(),
                entity.getFkClient(),
                entity.getFkEmployee(),
                entity.getFkPaymentType(),
                entity.getItems().stream()
                        .map(ScheduleMapper::toItemDomain)
                        .collect(Collectors.toSet())
        );
    }

    public static ScheduleJpaEntity toJpaEntity(ScheduleDomain domain) {
        if (domain == null) return null;

        ScheduleJpaEntity entity = new ScheduleJpaEntity();
        entity.setId(domain.getId());
        entity.setStatus(domain.getStatus());
        entity.setAppointmentDatetime(domain.getAppointmentDatetime());
        entity.setDuration(domain.getDuration());
        entity.setTransactionHash(domain.getTransactionHash());
        entity.setFkClient(domain.getFkClient());
        entity.setFkEmployee(domain.getFkEmployee());
        entity.setFkPaymentType(domain.getFkPaymentType());

        // Mapeia os itens do agendamento
        Set<ScheduleItemJpaEntity> items = domain.getItems().stream()
                .map(item -> toItemJpaEntity(item, entity))
                .collect(Collectors.toSet());
        entity.setItems(items);

        return entity;
    }

    private static ScheduleItemDomain toItemDomain(ScheduleItemJpaEntity entity) {
        return new ScheduleItemDomain(
                entity.getId(),
                entity.getFinalPrice(),
                entity.getDiscount(),
                entity.getFkService()
        );
    }

    private static ScheduleItemJpaEntity toItemJpaEntity(ScheduleItemDomain domain, ScheduleJpaEntity schedule) {
        ScheduleItemJpaEntity entity = new ScheduleItemJpaEntity();
        entity.setId(domain.getId());
        entity.setFinalPrice(domain.getFinalPrice());
        entity.setDiscount(domain.getDiscount());
        entity.setFkService(domain.getFkService());
        entity.setSchedule(schedule); // Relacionamento
        return entity;
    }
}
