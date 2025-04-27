package sptech.school.projetoPI.dto.schedule;

import sptech.school.projetoPI.dto.client.ClientMapper;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeMapper;
import sptech.school.projetoPI.dto.employee.EmployeeMapper;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

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

    public static Schedule toEntity(ScheduleUpdateRequestDto updateObject) {
        if(updateObject == null) return null;

        return Schedule.builder()
                .appointmentDatetime(updateObject.getAppointmentDatetime())
                .status(Status.checkValue(updateObject.getStatus().toUpperCase()))
                .duration(updateObject.getDuration())
                .transactionHash(updateObject.getTransactionHash())
                .updatedAt(LocalDateTime.now())
                .client(Client.builder().id(updateObject.getClient()).build())
                .employee(Employee.builder().id(updateObject.getEmployee()).build())
                .paymentType(PaymentType.builder().id(updateObject.getPaymentType()).build())
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
