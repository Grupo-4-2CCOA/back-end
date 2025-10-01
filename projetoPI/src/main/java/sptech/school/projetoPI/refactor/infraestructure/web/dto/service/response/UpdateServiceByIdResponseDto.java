package sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.category.CreateCategoryResponseDto;

public record UpdateServiceByIdResponseDto(
        Integer id,
        Boolean active,
        String name,
        Double basePrice,
        Integer baseDuration,
        String description,
        String image,
        CreateCategoryResponseDto category

) {
}
