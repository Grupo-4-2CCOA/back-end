package sptech.school.projetoPI.refactor.infraestructure.web.dto.service.request;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.category.CreateCategoryResponseDto;

public record CreateServiceRequestDto(
        Boolean active,
        String name,
        Double basePrice,
        Integer baseDuration,
        String description,
        String image,
        CreateCategoryResponseDto category
) {
}
