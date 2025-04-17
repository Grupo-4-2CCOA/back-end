package sptech.school.projetoPI.dto.feedback;

import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.dto.user.UserMapper;
import sptech.school.projetoPI.entities.Feedback;

public class FeedbackMapper {
    public static Feedback toEntity(FeedbackRequestDto requestObject) {
        Feedback feedback = new Feedback();

        feedback.setComment(requestObject.getComment());
        feedback.setRating(requestObject.getRating());
        feedback.setUser(UserMapper.toEntity(requestObject.getUser()));
        feedback.setSchedule(ScheduleMapper.toEntity(requestObject.getSchedule()));

        return feedback;
    }

    public static FeedbackResponseDto toResponseDto(Feedback entity) {
        FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();

        feedbackResponseDto.setId(entity.getId());
        feedbackResponseDto.setComment(entity.getComment());
        feedbackResponseDto.setRating(entity.getRating());
        feedbackResponseDto.setCreatedAt(entity.getCreatedAt());
        feedbackResponseDto.setUpdatedAt(entity.getUpdatedAt());
        feedbackResponseDto.setUser(UserMapper.toResponseDto(entity.getUser()));
        feedbackResponseDto.setSchedule(ScheduleMapper.toResponseDto(entity.getSchedule()));

        return feedbackResponseDto;
    }
}
