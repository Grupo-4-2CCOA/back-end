package sptech.school.projetoPI.dto.feedback;

import sptech.school.projetoPI.dto.client.ClientMapper;
import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;

public class FeedbackMapper {
    public static Feedback toEntity(FeedbackRequestDto requestObject) {
        if(requestObject == null) return null;

        return Feedback.builder()
                .comment(requestObject.getComment())
                .rating(requestObject.getRating())
                .client(Client.builder().id(requestObject.getClient()).build())
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
                .client(ClientMapper.toResumeResponseDto(entity.getClient()))
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
