package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleItemJpaEntity;

public class ScheduleItemMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleItem toDomain(ScheduleItemRequestDto requestObject) {
        if (requestObject == null) return null;

        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setFinalPrice(requestObject.getFinalPrice());
        scheduleItem.setDiscount(requestObject.getDiscount());

        // This is the correct call, assuming your DTO has Integer IDs
        scheduleItem.setSchedule(ScheduleMapper.toDomain(requestObject.getSchedule()));
        scheduleItem.setService(ServiceMapper.toDomain(requestObject.getService()));

        return scheduleItem;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static ScheduleItemResponseDto toResponseDto(ScheduleItem domain) {
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
    public static ScheduleItemResumeResponseDto toResumeResponseDto(ScheduleItem domain) {
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
    public static ScheduleItemJpaEntity toJpaEntity(ScheduleItem domain) {
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
    public static ScheduleItem toDomain(ScheduleItemJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        ScheduleItem domain = new ScheduleItem();
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