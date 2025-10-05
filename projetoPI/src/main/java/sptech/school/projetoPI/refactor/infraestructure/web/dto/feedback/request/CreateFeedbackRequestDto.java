package sptech.school.projetoPI.refactor.infraestructure.web.dto.feedback.request;

public record CreateFeedbackRequestDto(
        Integer rating,
        String comment,
        Integer scheduleId
) {
}
