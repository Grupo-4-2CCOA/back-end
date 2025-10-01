package sptech.school.projetoPI.refactor.infraestructure.web.dto.service.request;

public record CreateServiceRequestDto(
        Boolean active,
        String name,
        Double basePrice,
        Integer baseDuration,
        String description,
        String image
) {
}
