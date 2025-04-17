package sptech.school.projetoPI.dto.feedback;

import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.dto.user.UserMapper;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.User;

public class FeedbackMapper {
    public static Feedback toEntity(FeedbackRequestDto requestObject) {
        if(requestObject == null) return null;

        return Feedback.builder()
                .comment(requestObject.getComment())
                .rating(requestObject.getRating())
                .user(User.builder().id(requestObject.getUser()).build())
                .schedule(Schedule.builder().id(requestObject.getSchedule()).build())
                .build();
    }

    public static FeedbackResponseDto toResponseDto(Feedback entity) {
        if(entity == null) return null;

        return FeedbackResponseDto.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .user(UserMapper.toResumeResponseDto(entity.getUser()))
                .schedule(ScheduleMapper.toResumeResponseDto(entity.getSchedule()))
                .build();
    }

    public static FeedbackResumeResponseDto toResumeResponseDto(Feedback entity) {
        if(entity == null) return null;

        return FeedbackResumeResponseDto.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                .rating(entity.getRating())
                .build();
    }
}
