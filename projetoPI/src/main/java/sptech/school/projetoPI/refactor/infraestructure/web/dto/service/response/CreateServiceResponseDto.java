package sptech.school.projetoPI.refactor.infraestructure.web.dto.service.response;

public record CreateServiceResponseDto(
        Integer id,
        Boolean active,
        String name,
        Double basePrice,
        Integer baseDuration,
        String description,
        String image

) {
}
