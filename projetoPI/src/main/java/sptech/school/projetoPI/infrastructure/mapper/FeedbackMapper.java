package sptech.school.projetoPI.infrastructure.mapper;

import sptech.school.projetoPI.core.application.command.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.core.application.command.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.core.application.command.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;
import sptech.school.projetoPI.core.domain.FeedbackDomain;

public class FeedbackMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static FeedbackDomain toDomain(FeedbackRequestDto requestObject) {
        if (requestObject == null) return null;

        FeedbackDomain feedbackDomain = new FeedbackDomain();
        feedbackDomain.setComment(requestObject.getComment());
        feedbackDomain.setRating(requestObject.getRating());

        feedbackDomain.setClient(ClientMapper.toDomain(requestObject.getClient()));
        feedbackDomain.setSchedule(ScheduleMapper.toDomain(requestObject.getSchedule()));

        return feedbackDomain;
    }



    /* ========= DOMAIN -> DTO (Full Response) ========= */
    public static FeedbackResponseDto toResponseDto(FeedbackDomain domain) {
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
    public static FeedbackResumeResponseDto toResumeResponseDto(FeedbackDomain domain) {
        if (domain == null) return null;

        return FeedbackResumeResponseDto.builder()
                .id(domain.getId())
                .comment(domain.getComment())
                .rating(domain.getRating())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static FeedbackJpaEntity toJpaEntity(FeedbackDomain domain) {
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
    public static FeedbackDomain toDomain(FeedbackJpaEntity jpa) {
        if (jpa == null) return null;

        FeedbackDomain domain = new FeedbackDomain();
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