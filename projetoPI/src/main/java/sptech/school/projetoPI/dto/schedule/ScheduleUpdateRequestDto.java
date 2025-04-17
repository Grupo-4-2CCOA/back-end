package sptech.school.projetoPI.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @Positive(message = "Insira um tempo de duração válida")
    private Integer duration;

    @Size(max = 255, message = "Hash da Transação muito longa")
    private String transactionHash;
    private PaymentTypeResponseDto paymentType;
}
