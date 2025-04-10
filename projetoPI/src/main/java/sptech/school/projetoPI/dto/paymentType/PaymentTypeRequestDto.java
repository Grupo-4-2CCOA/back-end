package sptech.school.projetoPI.dto.paymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PaymentTypeRequestDto {
    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Preencha o nome do Tipo de Pagamento")
    private String name;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
