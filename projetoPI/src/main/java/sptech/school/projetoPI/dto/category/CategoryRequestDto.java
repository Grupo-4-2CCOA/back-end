package sptech.school.projetoPI.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Insira o nome da categoria")
    private String name;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;
}
