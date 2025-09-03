package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.domains.Client;

public class ScheduleMapper {
    public static Schedule toDomain(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        Employee employee = (requestObject.getEmployee() != null) ?
                EmployeeMapper.toDomain(requestObject.getEmployee()) : null;

        Client client = (requestObject.getClient() != null) ?
                ClientMapper.toDomain(requestObject.getClient()) : null;

        Schedule schedule = new Schedule();
        schedule.setEmployee(employee);
        schedule.setClient(client);
        schedule.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        schedule.setStatus(requestObject.getStatus().);
    }

    public static ScheduleResponseDto toResponseDto(Schedule entity) {
        if(entity == null) return null;

        return ScheduleResponseDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .appointmentDatetime(entity.getAppointmentDatetime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .client(ClientMapper.toResumeResponseDto(entity.getClient()))
                .employee(EmployeeMapper.toResumeResponseDto(entity.getEmployee()))
                .paymentType(PaymentTypeMapper.toResumeResponseDto(entity.getPaymentType()))
                .build();
    }

    public static ScheduleResumeResponseDto toResumeResponseDto(Schedule entity) {
        if(entity == null) return null;

        return ScheduleResumeResponseDto.builder()
                .id(entity.getId())
                .appointmentDatetime(entity.getAppointmentDatetime())
                .status(entity.getStatus())
                .client(ClientMapper.toResumeResponseDto(entity.getClient()))
                .employee(EmployeeMapper.toResumeResponseDto(entity.getEmployee()))
                .build();
    }
}
