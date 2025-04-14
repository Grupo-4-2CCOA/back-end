package sptech.school.projetoPI.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleUpdateRequestDto {
    @NotNull(message = "Preencha com o ID do Usuário")
    private UserResponseDto user;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "Horário deve estar no futuro")
    private LocalDateTime appointmentDatetime;

    private String status;
    private Integer duration;
    private String transactionHash;
    private PaymentTypeResponseDto paymentType;
}
