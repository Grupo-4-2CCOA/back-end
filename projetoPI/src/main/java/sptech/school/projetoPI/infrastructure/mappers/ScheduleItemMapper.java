package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleItemJpaEntity;

public class ScheduleItemMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleItemDomain toDomain(ScheduleItemRequestDto requestObject) {
        if (requestObject == null) return null;

        ScheduleItemDomain scheduleItemDomain = new ScheduleItemDomain();
        scheduleItemDomain.setFinalPrice(requestObject.getFinalPrice());
        scheduleItemDomain.setDiscount(requestObject.getDiscount());

        // This is the correct call, assuming your DTO has Integer IDs
        scheduleItemDomain.setSchedule(ScheduleMapper.toDomain(requestObject.getSchedule()));
        scheduleItemDomain.setService(ServiceMapper.toDomain(requestObject.getService()));

        return scheduleItemDomain;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static ScheduleItemResponseDto toResponseDto(ScheduleItemDomain domain) {
        if (domain == null) return null;

        return ScheduleItemResponseDto.builder()
                .id(domain.getId())
                .finalPrice(domain.getFinalPrice())
                .discount(domain.getDiscount())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .schedule(ScheduleMapper.toResumeResponseDto(domain.getSchedule()))
                .service(ServiceMapper.toResumeResponseDto(domain.getService()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Resumo) ========= */
    public static ScheduleItemResumeResponseDto toResumeResponseDto(ScheduleItemDomain domain) {
        if (domain == null) return null;

        return ScheduleItemResumeResponseDto.builder()
                .id(domain.getId())
                .finalPrice(domain.getFinalPrice())
                .schedule(ScheduleMapper.toResumeResponseDto(domain.getSchedule()))
                .service(ServiceMapper.toResumeResponseDto(domain.getService()))
                .build();
    }

    //---------------------------------------------------------

    /* ========= DOMAIN -> JPA ========= */
    public static ScheduleItemJpaEntity toJpaEntity(ScheduleItemDomain domain) {
        if (domain == null) return null;

        ScheduleItemJpaEntity jpaEntity = new ScheduleItemJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setFinalPrice(domain.getFinalPrice());
        jpaEntity.setDiscount(domain.getDiscount());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getSchedule() != null) {
            jpaEntity.setSchedule(ScheduleMapper.toJpaEntity(domain.getSchedule()));
        }
        if (domain.getService() != null) {
            jpaEntity.setService(ServiceMapper.toJpaEntity(domain.getService()));
        }

        return jpaEntity;
    }

    /* ========= JPA -> DOMAIN ========= */
    public static ScheduleItemDomain toDomain(ScheduleItemJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        ScheduleItemDomain domain = new ScheduleItemDomain();
        domain.setId(jpaEntity.getId());
        domain.setFinalPrice(jpaEntity.getFinalPrice());
        domain.setDiscount(jpaEntity.getDiscount());
        domain.setCreatedAt(jpaEntity.getCreatedAt());
        domain.setUpdatedAt(jpaEntity.getUpdatedAt());

        domain.setSchedule(ScheduleMapper.toDomain(jpaEntity.getSchedule()));
        domain.setService(ServiceMapper.toDomain(jpaEntity.getService()));

        return domain;
    }
}