package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleJpaEntity;


public class ScheduleMapper {


    /* ========= DTO -> DOMAIN ========= */
    public static Schedule toDomain(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        Schedule schedule = new Schedule();
        schedule.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        schedule.setStatus(Status.valueOf(requestObject.getStatus())); // Correção aqui

        // Delegação de mapeamento para outras entidades
        schedule.setEmployee(EmployeeMapper.toDomain(requestObject.getEmployee()));
        schedule.setClient(ClientMapper.toDomain(requestObject.getClient()));
        schedule.setPaymentType(PaymentTypeMapper.toDomain(requestObject.getPaymentType()));

        return schedule;
    }

    public static Schedule toDomain(Integer id) {
        if (id == null) return null;

        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static ScheduleResponseDto toResponseDto(Schedule domain) {
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
    public static ScheduleResumeResponseDto toResumeResponseDto(Schedule domain) {
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
    public static ScheduleJpaEntity toJpaEntity(Schedule domain) {
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
    public static Schedule toDomain(ScheduleJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        Schedule domain = new Schedule();
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