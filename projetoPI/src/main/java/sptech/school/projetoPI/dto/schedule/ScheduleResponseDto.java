package sptech.school.projetoPI.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

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

    private ClientResumeResponseDto client;
    private EmployeeResumeResponseDto employee;
    private PaymentTypeResumeResponseDto paymentType;
}
