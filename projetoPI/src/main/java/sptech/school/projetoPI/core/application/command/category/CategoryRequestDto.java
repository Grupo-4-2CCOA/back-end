package sptech.school.projetoPI.core.application.command.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Insira o nome da categoria")
    private String name;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;
}
