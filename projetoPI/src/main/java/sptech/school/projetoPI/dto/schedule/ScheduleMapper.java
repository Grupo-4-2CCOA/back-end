package sptech.school.projetoPI.dto.schedule;

import sptech.school.projetoPI.dto.user.UserMapper;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

public class ScheduleMapper {
    public static Schedule toEntity(ScheduleRequestDto requestObject) {
        if(requestObject == null) return null;

        return Schedule.builder()
                .appointmentDatetime(requestObject.getAppointmentDatetime())
                .status(Status.ACTIVE)
                .user(User.builder().id(requestObject.getUser()).build())
                .build();
    }

    public static Schedule toEntity(ScheduleUpdateRequestDto updateObject) {
        if(updateObject == null) return null;

        return Schedule.builder()
                .appointmentDatetime(updateObject.getAppointmentDatetime())
                .status(Status.valueOf(updateObject.getStatus()))
                .duration(updateObject.getDuration())
                .transactionHash(updateObject.getTransactionHash())
                .updatedAt(LocalDateTime.now())
                .user(User.builder().id(updateObject.getUser()).build())
                .paymentType(
                        updateObject.getPaymentType() != null?
                        PaymentType.builder().id(updateObject.getPaymentType()).build() :
                        null
                )
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
                .user(UserMapper.toResumeResponseDto(entity.getUser()))
//                .paymentType(
//                        entity.getPaymentType() != null?
//                                PaymentType.builder().id(entity.getPaymentType()).build() :
//                                null
//                )
                .build();
    }

    public static ScheduleResumeResponseDto toResumeResponseDto(Schedule entity) {
        if(entity == null) return null;

        return ScheduleResumeResponseDto.builder()
                .id(entity.getId())
                .appointmentDatetime(entity.getAppointmentDatetime())
                .status(entity.getStatus())
                .user(UserMapper.toResumeResponseDto(entity.getUser()))
                .build();
    }
}
