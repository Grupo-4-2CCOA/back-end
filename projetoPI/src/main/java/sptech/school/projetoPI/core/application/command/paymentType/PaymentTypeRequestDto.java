package sptech.school.projetoPI.core.application.command.paymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeRequestDto {
    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Preencha o nome do Tipo de Pagamento")
    private String name;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;
}
