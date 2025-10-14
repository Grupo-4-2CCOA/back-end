package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleItemJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.entity.ServiceJpaEntity;

import java.time.LocalDateTime;
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

        // ✅ Apenas ID do serviço (evita loops)
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
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

    /* ========= DOMAIN -> JPA ========= */
    public static ScheduleItemJpaEntity toJpaEntity(ScheduleItemDomain domain) {
        if (domain == null) return null;

        ScheduleItemJpaEntity jpa = new ScheduleItemJpaEntity();
        jpa.setId(domain.getId());
        jpa.setCreatedAt(LocalDateTime.now());
        jpa.setUpdatedAt(LocalDateTime.now());
        jpa.setFinalPrice(domain.getFinalPrice());
        jpa.setDiscount(domain.getDiscount());

        // ✅ Cria entidade Service apenas com ID
        if (domain.getService() != null && domain.getService().getId() != null) {
            ServiceJpaEntity serviceRef = new ServiceJpaEntity();
            serviceRef.setId(domain.getService().getId());
            jpa.setService(serviceRef);
        }

        return jpa;
    }

    /* ========= JPA -> DOMAIN ========= */
    public static ScheduleItemDomain toDomain(ScheduleItemJpaEntity jpa) {
        if (jpa == null) return null;

        ScheduleItemDomain domain = new ScheduleItemDomain();
        domain.setId(jpa.getId());
        domain.setCreatedAt(LocalDateTime.now());
        domain.setUpdatedAt(LocalDateTime.now());
        domain.setFinalPrice(jpa.getFinalPrice());
        domain.setDiscount(jpa.getDiscount());

        domain.setService(ServiceMapper.toDomain(jpa.getService()));

        return domain;
    }

    /* ========= DOMAIN -> JPA (Lista) ========= */
    public static List<ScheduleItemJpaEntity> toJpaEntityList(List<ScheduleItemDomain> domains) {
        if (domains == null || domains.isEmpty()) {
            return Collections.emptyList();
        }
        return domains.stream()
                .map(ScheduleItemMapper::toJpaEntity)
                .collect(Collectors.toList());
    }
}