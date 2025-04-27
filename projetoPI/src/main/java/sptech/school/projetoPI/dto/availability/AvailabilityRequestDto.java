package sptech.school.projetoPI.dto.availability;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequestDto {
    @FutureOrPresent(message = "Data deve estar no presente/futuro")
    @NotNull(message = "Insira o nome")
    private LocalDate day;

    @NotNull(message = "Insira um horário inicial")
    private LocalTime startTime;

    @NotNull(message = "Insira um horário final")
    private LocalTime endTime;

    @Positive(message = "ID para funcionário inválido")
    @NotNull(message = "Insira um funcionário")
    private Integer employee;
}
