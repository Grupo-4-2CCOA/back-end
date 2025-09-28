package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.old.core.application.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.old.infrastructure.mappers.ServiceMapper;
import sptech.school.projetoPI.refactor.core.application.command.scheduleItem.CreateScheduleItemCommand;
import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleItemDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.ScheduleItemJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.CreateScheduleItemRequestDto;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ScheduleItemMapper {
    // DTO -> Command
    public static CreateScheduleItemCommand toCreateScheduleItemCommand(CreateScheduleItemRequestDto dto) {
        if (dto == null) return null;

        return new CreateScheduleItemCommand(
                dto.finalPrice(),
                dto.discount(),
                dto.serviceId()
        );
    }

    // Set<DTO> -> Set<Command>
    public static Set<CreateScheduleItemCommand> toCreateScheduleItemCommandSet(Set<ScheduleItemRequestDto> dtoSet) {
        if (dtoSet == null || dtoSet.isEmpty()) {
            return Collections.emptySet();
        }

        return dtoSet.stream()
                .map(ScheduleItemMapper::toCreateScheduleItemCommand)
                .collect(Collectors.toSet());
    }

    // Domain -> JpaEntity
    public static ScheduleItemJpaEntity toScheduleItemJpaEntity(ScheduleItemDomain domain) {
        if (domain == null) return null;

        ScheduleItemJpaEntity entity = new ScheduleItemJpaEntity();
        entity.setId(domain.getId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setFinalPrice(domain.getFinalPrice());
        entity.setDiscount(domain.getDiscount());
        entity.setService(ServiceMapper.toServiceJpaEntity(domain.getServiceDomain()));
        return entity;
    }

    // JpaEntity -> Domain
    public static ScheduleItemDomain toScheduleItemDomain(ScheduleItemJpaEntity entity) {
        if (entity == null) return null;

        ScheduleItemDomain domain = new ScheduleItemDomain();
        domain.setId(entity.getId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setFinalPrice(entity.getFinalPrice());
        domain.setDiscount(entity.getDiscount());
        domain.setServiceDomain(ServiceMapper.toServiceDomain(entity.getService()));
        return domain;
    }
}
