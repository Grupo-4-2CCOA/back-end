package sptech.school.projetoPI.dto.schedule;

import sptech.school.projetoPI.dto.paymentType.PaymentTypeMapper;
import sptech.school.projetoPI.dto.user.UserMapper;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

public class ScheduleMapper {
    public static Schedule toEntity(ScheduleRequestDto requestObject) {
        Schedule schedule = new Schedule();

        schedule.setAppointmentDatetime(requestObject.getAppointmentDatetime());
        schedule.setUser(UserMapper.toEntity(requestObject.getUser()));
        schedule.setStatus(Status.ACTIVE);

        return schedule;
    }

    public static Schedule toEntity(ScheduleUpdateRequestDto updateObject) {
        Schedule schedule = new Schedule();

        schedule.setAppointmentDatetime(updateObject.getAppointmentDatetime());
        schedule.setStatus(Status.statusCheck(updateObject.getStatus()));
        schedule.setDuration(updateObject.getDuration());
        schedule.setTransactionHash(updateObject.getTransactionHash());
        schedule.setUpdatedAt(LocalDateTime.now());
        schedule.setUser(UserMapper.toEntity(updateObject.getUser()));
        if(updateObject.getPaymentType() != null) schedule.setPaymentType(PaymentTypeMapper.toEntity(updateObject.getPaymentType()));

        return schedule;
    }

    public static Schedule toEntity(ScheduleResponseDto responseObject) {
        Schedule schedule = new Schedule();
        schedule.setId(responseObject.getId());
        return schedule;
    }

    public static ScheduleResponseDto toResponseDto(Schedule entity) {
        ScheduleResponseDto schedule = new ScheduleResponseDto();

        schedule.setId(entity.getId());
        schedule.setStatus(entity.getStatus());
        schedule.setAppointmentDatetime(entity.getAppointmentDatetime());
        schedule.setCreatedAt(entity.getCreatedAt());
        schedule.setUpdatedAt(entity.getUpdatedAt());
        schedule.setUser(UserMapper.toResponseDto(entity.getUser()));
        if(entity.getPaymentType() != null) schedule.setPaymentType(PaymentTypeMapper.toResponseDto(entity.getPaymentType()));

        return schedule;
    }
}
