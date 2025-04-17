package sptech.school.projetoPI.dto.service;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.dto.category.CategoryResponseDto;

@Getter
@Setter
public class ServiceRequestDto {
    @Size(max = 80)
    @NotBlank(message = "Preencha o nome do serviço")
    private String name;

    @Positive(message = "Preço deve ser positivo")
    @DecimalMax(value = "999.99", message = "Preço muito alto")
    @NotNull(message = "Preencha o valor do serviço")
    private Double basePrice;

    @Max(value = 1000, message = "Muito longo")
    @Min(value = 1, message = "Muito curto")
    @NotNull(message = "Preencha a duração do serviço")
    private Integer baseDuration;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;

    @Size(max = 255, message = "URL de imagem muito longa")
    private String image;

    @NotNull(message = "Preencha o ID da categoria")
    private CategoryResponseDto category;
}
