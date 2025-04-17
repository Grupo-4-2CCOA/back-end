package sptech.school.projetoPI.dto.scheduleItem;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItemRequestDto {
    @NotNull(message = "Insira o valor do preço total")
    @Positive(message = "Insira um valor válido")
    @DecimalMax(value = "999.99", message = "Valor muito alto")
    private Double finalPrice;

    @NotNull(message = "Insira o valor do disconto")
    @Positive(message = "Insira um desconto válido")
    @DecimalMax(value = "999.99", message = "Desconto muito alto")
    private Double discount;

    @Positive(message = "ID inválido para Agendamento")
    @NotNull(message = "Insira o Agendamento")
    private Integer schedule;

    @Positive(message = "ID inválido para Serviço")
    @NotNull(message = "Insira o Serviço")
    private Integer service;
}
