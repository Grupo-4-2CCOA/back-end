package sptech.school.projetoPI.infrastructure.mapper;

import sptech.school.projetoPI.core.domain.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.application.command.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.core.application.command.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.core.application.command.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;


public class ScheduleMapper {


    /* ========= DTO -> DOMAIN ========= */
    public static ScheduleDomain toDomain(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        scheduleDomain.setStatus(Status.valueOf(requestObject.getStatus())); // Correção aqui

        // Delegação de mapeamento para outras entidades
        scheduleDomain.setEmployee(EmployeeMapper.toDomain(requestObject.getEmployee()));
        scheduleDomain.setClient(ClientMapper.toDomain(requestObject.getClient()));
        scheduleDomain.setPaymentType(PaymentTypeMapper.toDomain(requestObject.getPaymentType()));

        return scheduleDomain;
    }

    public static ScheduleDomain toDomain(Integer id) {
        if (id == null) return null;

        ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setId(id);
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
                .client(ClientMapper.toResumeResponseDto(domain.getClient()))
                .employee(EmployeeMapper.toResumeResponseDto(domain.getEmployee()))
                .paymentType(PaymentTypeMapper.toResumeResponseDto(domain.getPaymentType()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Resumo) ========= */
    public static ScheduleResumeResponseDto toResumeResponseDto(ScheduleDomain domain) {
        if(domain == null) return null;

        return ScheduleResumeResponseDto.builder()
                .id(domain.getId())
                .appointmentDatetime(domain.getAppointmentDatetime())
                .status(domain.getStatus())
                .client(ClientMapper.toResumeResponseDto(domain.getClient()))
                .employee(EmployeeMapper.toResumeResponseDto(domain.getEmployee()))
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
        if (domain.getClient() != null) {
            jpaEntity.setClient(ClientMapper.toJpaEntity(domain.getClient()));
        }

        if (domain.getEmployee() != null) {
            jpaEntity.setEmployee(EmployeeMapper.toJpaEntity(domain.getEmployee()));
        }

        if (domain.getPaymentType() != null) {
            jpaEntity.setPaymentType(PaymentTypeMapper.toJpaEntity(domain.getPaymentType()));
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
        domain.setClient(ClientMapper.toDomain(jpaEntity.getClient()));
        domain.setEmployee(EmployeeMapper.toDomain(jpaEntity.getEmployee()));
        domain.setPaymentType(PaymentTypeMapper.toDomain(jpaEntity.getPaymentType()));

        return domain;
    }
}