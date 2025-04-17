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
    @Positive(message = "ID inválido para usuário")
    @NotNull(message = "Preencha com o ID do Usuário")
    private Integer user;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "Horário deve estar no futuro")
    private LocalDateTime appointmentDatetime;
}
