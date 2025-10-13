package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleItemJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;

import java.util.List;


public class ScheduleMapper {


    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleDomain toDomain(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        scheduleDomain.setStatus(Status.valueOf(requestObject.getStatus()));

        // Delegação de mapeamento para outras entidades
        scheduleDomain.setEmployeeDomain(UserMapper.toDomain(requestObject.getEmployee()));
        scheduleDomain.setClientDomain(UserMapper.toDomain(requestObject.getClient()));
        scheduleDomain.setPaymentTypeDomain(PaymentTypeMapper.toDomain(requestObject.getPaymentType()));

        // NOVO: Mapeia a lista de itens
        scheduleDomain.setItems(ScheduleItemMapper.toDomainList(requestObject.getItems()));

        return scheduleDomain;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static ScheduleResponseDto toResponseDto(ScheduleDomain domain) {
        if(domain == null) return null;

        return ScheduleResponseDto.builder()
                .id(domain.getId())
                .status(domain.getStatus())
                .appointmentDatetime(domain.getAppointmentDatetime())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .client(UserMapper.toResumeResponseDto(domain.getClientDomain()))
                .employee(UserMapper.toResumeResponseDto(domain.getEmployeeDomain()))
                .paymentType(PaymentTypeMapper.toResumeResponseDto(domain.getPaymentTypeDomain()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Resumo) ========= */
    public static ScheduleResumeResponseDto toResumeResponseDto(ScheduleDomain domain) {
        if(domain == null) return null;

        return ScheduleResumeResponseDto.builder()
                .id(domain.getId())
                .appointmentDatetime(domain.getAppointmentDatetime())
                .status(domain.getStatus())
                .client(UserMapper.toResumeResponseDto(domain.getClientDomain()))
                .employee(UserMapper.toResumeResponseDto(domain.getEmployeeDomain()))
                .build();
    }

    // Mapeamento de Domínio para Entidade de Persistência (usado no Repositório)
    public static ScheduleJpaEntity toJpaEntity(ScheduleDomain domain) {
        if (domain == null) return null;

        ScheduleJpaEntity jpaEntity = new ScheduleJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setStatus(domain.getStatus());
        jpaEntity.setAppointmentDatetime(domain.getAppointmentDatetime());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());

        // Delega o mapeamento de entidades aninhadas
        if (domain.getClientDomain() != null) {
            jpaEntity.setClient(UserMapper.toJpaEntity(domain.getClientDomain()));
        }

        if (domain.getEmployeeDomain() != null) {
            jpaEntity.setEmployee(UserMapper.toJpaEntity(domain.getEmployeeDomain()));
        }

        if (domain.getPaymentTypeDomain() != null) {
            jpaEntity.setPaymentType(PaymentTypeMapper.toJpaEntity(domain.getPaymentTypeDomain()));
        }

        if (domain.getItems() != null && !domain.getItems().isEmpty()) {

            // 1. Converte a lista de Domínio para JPA Entity
            List<ScheduleItemJpaEntity> itemEntities = ScheduleItemMapper.toJpaEntityList(domain.getItems());

            // 2. IMPORTANTE: Garante a referência bidirecional (seta o pai em cada filho)
            itemEntities.forEach(item -> item.setSchedule(jpaEntity));

            // 3. Seta a lista na entidade pai
            jpaEntity.setItems(itemEntities);
        }

        return jpaEntity;
    }

    // Mapeamento de Entidade de Persistência para Domínio (usado no Repositório)
    public static ScheduleDomain toDomain(ScheduleJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        ScheduleDomain domain = new ScheduleDomain();
        domain.setId(jpaEntity.getId());
        domain.setStatus(jpaEntity.getStatus());
        domain.setAppointmentDatetime(jpaEntity.getAppointmentDatetime());
        domain.setCreatedAt(jpaEntity.getCreatedAt());
        domain.setUpdatedAt(jpaEntity.getUpdatedAt());

        // Delega o mapeamento de entidades aninhadas
        domain.setClientDomain(UserMapper.toDomain(jpaEntity.getClient()));
        domain.setEmployeeDomain(UserMapper.toDomain(jpaEntity.getEmployee()));
        domain.setPaymentTypeDomain(PaymentTypeMapper.toDomain(jpaEntity.getPaymentType()));

        return domain;
    }
}