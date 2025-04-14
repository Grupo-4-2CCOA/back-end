package sptech.school.projetoPI.dto.schedule;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime appointmentDatetime;

    private UserResponseDto user;
    private PaymentTypeResponseDto paymentType;
}
