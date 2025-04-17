package sptech.school.projetoPI.dto.schedule;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;
import sptech.school.projetoPI.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime appointmentDatetime;

    private UserResumeResponseDto user;
    private PaymentTypeResumeResponseDto paymentType;
}
