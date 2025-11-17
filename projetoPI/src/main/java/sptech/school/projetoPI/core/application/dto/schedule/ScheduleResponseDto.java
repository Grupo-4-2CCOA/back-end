package sptech.school.projetoPI.core.application.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime appointmentDatetime;
    private Integer duration;

    private UserResumeResponseDto client;
    private UserResumeResponseDto employee;
    private PaymentTypeResumeResponseDto paymentType;
    private List<ScheduleItemResponseDto> items;
}
