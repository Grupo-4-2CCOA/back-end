package sptech.school.projetoPI.dto.schedule;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
}
