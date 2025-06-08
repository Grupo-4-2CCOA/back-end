package sptech.school.projetoPI.dto.schedule;

import sptech.school.projetoPI.dto.client.ClientMapper;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeMapper;
import sptech.school.projetoPI.dto.employee.EmployeeMapper;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.enums.Status;

public class ScheduleMapper {
    public static Schedule toEntity(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        return Schedule.builder()
                .appointmentDatetime(requestObject.getAppointmentDatetime())
                .status(Status.ACTIVE)
                .client(Client.builder().id(requestObject.getClient()).build())
                .employee(Employee.builder().id(requestObject.getEmployee()).build())
                .build();
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
