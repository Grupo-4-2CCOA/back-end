package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;

import java.util.List;
import java.util.stream.Collectors;


public class ScheduleMapper {


    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleDomain toDomain(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        scheduleDomain.setStatus(Status.valueOf(requestObject.getStatus()));
        scheduleDomain.setDuration(requestObject.getDuration());

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
                .duration(domain.getDuration())
                .client(UserMapper.toResumeResponseDto(domain.getClientDomain()))
                .employee(UserMapper.toResumeResponseDto(domain.getEmployeeDomain()))
                .paymentType(PaymentTypeMapper.toResumeResponseDto(domain.getPaymentTypeDomain()))
                .items(domain.getItems() != null
                        ? domain.getItems().stream()
                        .map(ScheduleItemMapper::toResponseDto)
                        .collect(Collectors.toList())
                        : List.of())
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

    public static ScheduleJpaEntity toJpaEntity(ScheduleDomain domain) {
        if (domain == null) return null;

        ScheduleJpaEntity jpa = new ScheduleJpaEntity();
        jpa.setId(domain.getId());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        jpa.setStatus(domain.getStatus());
        jpa.setAppointmentDatetime(domain.getAppointmentDatetime());
        jpa.setTransactionHash(domain.getTransactionHash());
        jpa.setDuration(domain.getDuration());

        // Relacionamentos simples (evita loop)
        jpa.setClient(UserMapper.toJpaEntitySimple(domain.getClientDomain()));
        jpa.setEmployee(UserMapper.toJpaEntitySimple(domain.getEmployeeDomain()));
        jpa.setPaymentType(PaymentTypeMapper.toJpaEntity(domain.getPaymentTypeDomain()));

        // Itens
        if (domain.getItems() != null) {
            jpa.setItems(domain.getItems().stream()
                    .map(ScheduleItemMapper::toJpaEntity)
                    .collect(Collectors.toList()));
            jpa.getItems().forEach(item -> item.setSchedule(jpa));
        }

        return jpa;
    }

    public static ScheduleDomain toDomain(ScheduleJpaEntity jpa) {
        if (jpa == null) return null;

        ScheduleDomain domain = new ScheduleDomain();
        domain.setId(jpa.getId());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        domain.setStatus(jpa.getStatus());
        domain.setAppointmentDatetime(jpa.getAppointmentDatetime());
        domain.setTransactionHash(jpa.getTransactionHash());
        domain.setDuration(jpa.getDuration());
        domain.setClientDomain(UserMapper.toDomain(jpa.getClient()));
        domain.setEmployeeDomain(UserMapper.toDomain(jpa.getEmployee()));
        domain.setPaymentTypeDomain(PaymentTypeMapper.toDomain(jpa.getPaymentType()));

        if (jpa.getItems() != null) {
            domain.setItems(jpa.getItems().stream()
                    .map(ScheduleItemMapper::toDomain)
                    .collect(Collectors.toList()));
        }

        return domain;
    }

}