package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.infrastructure.dto.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.FeedbackJpaEntity;
import sptech.school.projetoPI.core.domains.Feedback;

public class FeedbackMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static Feedback toDomain(FeedbackRequestDto requestObject) {
        if (requestObject == null) return null;

        Feedback feedback = new Feedback();
        feedback.setComment(requestObject.getComment());
        feedback.setRating(requestObject.getRating());

        feedback.setClient(ClientMapper.toDomain(requestObject.getClient()));
        feedback.setSchedule(ScheduleMapper.toDomain(requestObject.getSchedule()));

        return feedback;
    }



    /* ========= DOMAIN -> DTO (Full Response) ========= */
    public static FeedbackResponseDto toResponseDto(Feedback domain) {
        if (domain == null) return null;

        return FeedbackResponseDto.builder()
                .id(domain.getId())
                .comment(domain.getComment())
                .rating(domain.getRating())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .client(ClientMapper.toResumeResponseDto(domain.getClient()))
                .schedule(ScheduleMapper.toResumeResponseDto(domain.getSchedule()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Resume Response) ========= */
    public static FeedbackResumeResponseDto toResumeResponseDto(Feedback domain) {
        if (domain == null) return null;

        return FeedbackResumeResponseDto.builder()
                .id(domain.getId())
                .comment(domain.getComment())
                .rating(domain.getRating())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static FeedbackJpaEntity toJpaEntity(Feedback domain) {
        if (domain == null) return null;

        return FeedbackJpaEntity.builder()
                .id(domain.getId())
                .comment(domain.getComment())
                .rating(domain.getRating())
                .client(ClientMapper.toJpaEntity(domain.getClient()))
                .schedule(ScheduleMapper.toJpaEntity(domain.getSchedule()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= JPA -> DOMAIN ========= */
    public static Feedback toDomain(FeedbackJpaEntity jpa) {
        if (jpa == null) return null;

        Feedback domain = new Feedback();
        domain.setId(jpa.getId());
        domain.setComment(jpa.getComment());
        domain.setRating(jpa.getRating());
        domain.setClient(ClientMapper.toDomain(jpa.getClient()));
        domain.setSchedule(ScheduleMapper.toDomain(jpa.getSchedule()));
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}