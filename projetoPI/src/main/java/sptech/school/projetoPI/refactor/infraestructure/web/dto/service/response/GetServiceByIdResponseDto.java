package sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response;

public record GetServiceByIdResponseDto(
        Integer id,
        Boolean active,
        String name,
        Double basePrice,
        Integer baseDuration,
        String description,
        String image

) {
}
