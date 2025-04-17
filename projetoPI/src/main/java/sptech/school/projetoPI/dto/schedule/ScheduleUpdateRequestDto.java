package sptech.school.projetoPI.dto.schedule;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequestDto {
    @Positive(message = "ID inválido para Usuário")
    @NotNull(message = "Preencha com o ID do Usuário")
    private Integer user;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "Horário deve estar no futuro")
    private LocalDateTime appointmentDatetime;

    @Pattern(regexp = "(?i)ACTIVE|COMPLETED|CANCELED", message = "O status do Agendamento deve ser ACTIVE, COMPLETED ou CANCELED")
    private String status;

    @Positive(message = "Insira um tempo de duração válida")
    private Integer duration;

    @Size(max = 255, message = "Hash da Transação muito longa")
    private String transactionHash;

    @Positive(message = "ID inválido para Tipo de Pagamento")
    private Integer paymentType;
}
