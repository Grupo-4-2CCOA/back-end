package sptech.school.projetoPI.old.core.application.dto.schedule;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    @Positive(message = "ID inválido para Cliente")
    @NotNull(message = "Preencha com o ID do Cliente")
    private Integer client;

    @Positive(message = "ID inválido para Funcionário")
    @NotNull(message = "Preencha com o ID do Funcionário")
    private Integer employee;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "Horário deve estar no futuro")
    private LocalDateTime appointmentDatetime;

    @NotBlank(message = "Insira o status")
    @Column(columnDefinition = "varchar(15) default 'ACTIVE'")
    @Pattern(regexp = "(?i)ACTIVE|COMPLETED|CANCELED", message = "O status deve ser ACTIVE, COMPLETED ou CANCELED")
    private String status;

    @Positive(message = "Insira um tempo de duração válida")
    private Integer duration;

    @Size(max = 255, message = "Hash da Transação muito longa")
    private String transactionHash;

    @Positive(message = "ID inválido para Tipo de Pagamento")
    private Integer paymentType;
}
