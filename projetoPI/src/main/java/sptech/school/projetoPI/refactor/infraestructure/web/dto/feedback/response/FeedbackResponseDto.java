package sptech.school.projetoPI.refactor.infraestructure.web.dto.feedback.response;

import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;

public record FeedbackResponseDto(
        Integer id,
        Integer rating,
        String comment,
        ScheduleDomain schedule
) {
}
