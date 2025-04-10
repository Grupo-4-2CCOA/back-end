package sptech.school.projetoPI.dto.feedback;

import sptech.school.projetoPI.entities.Feedback;

public class FeedbackMapper {
    public static Feedback toEntity(FeedbackRequestDto requestObject) {
        Feedback feedback = new Feedback();

        feedback.setComment(requestObject.getComment());
        feedback.setRating(requestObject.getRating());

        return feedback;
    }

    public static FeedbackResponseDto toResponseDto(Feedback entity) {
        FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();

        feedbackResponseDto.setId(entity.getId());
        feedbackResponseDto.setComment(entity.getComment());
        feedbackResponseDto.setRating(entity.getRating());
        feedbackResponseDto.setCreatedAt(entity.getCreatedAt());
        feedbackResponseDto.setUpdatedAt(entity.getUpdatedAt());

        return feedbackResponseDto;
    }
}
