package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleItemJpaEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleItemMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleItemDomain toDomain(ScheduleItemRequestDto requestObject) {
        if (requestObject == null) return null;

        ScheduleItemDomain scheduleItemDomain = new ScheduleItemDomain();
        scheduleItemDomain.setFinalPrice(requestObject.getFinalPrice());
        scheduleItemDomain.setDiscount(requestObject.getDiscount());

        scheduleItemDomain.setService(ServiceMapper.toDomain(requestObject.getService()));

        return scheduleItemDomain;
    }

    public static List<ScheduleItemDomain> toDomainList(List<ScheduleItemRequestDto> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(ScheduleItemMapper::toDomain).collect(Collectors.toList());
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

    /* ========= DOMAIN -> JPA (Lista) ========= */
    /**
     * Converte uma lista de objetos ScheduleItemDomain para uma lista de ScheduleItemJpaEntity.
     * @param domains Lista de ScheduleItemDomain.
     * @return Lista de ScheduleItemJpaEntity.
     */
    public static List<ScheduleItemJpaEntity> toJpaEntityList(List<ScheduleItemDomain> domains) {
        if (domains == null || domains.isEmpty()) {
            return Collections.emptyList();
        }
        return domains.stream()
                .map(ScheduleItemMapper::toJpaEntity)
                .collect(Collectors.toList());
    }
}