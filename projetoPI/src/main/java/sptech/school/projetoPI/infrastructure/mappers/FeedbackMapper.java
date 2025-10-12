package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.application.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.core.application.dto.feedback.FeedbackResponseDto;
import sptech.school.projetoPI.core.application.dto.feedback.FeedbackResumeResponseDto;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.infrastructure.persistence.entity.FeedbackJpaEntity;
import sptech.school.projetoPI.core.domains.FeedbackDomain;

import java.time.LocalDateTime;

public class FeedbackMapper {
    /* ========= REQUEST DTO -> DOMAIN ========= */
    public static FeedbackDomain toDomain(FeedbackRequestDto request) {
        if (request == null) return null;

        FeedbackDomain domain = new FeedbackDomain();
        domain.setComment(request.getComment());
        domain.setRating(request.getRating());

        // Mapeia o usuário (cliente)
        if (request.getClientId() != null) {
            var userDomain = new UserDomain();
            userDomain.setId(request.getClientId());
            domain.setUserDomain(userDomain);
        }

        // Mapeia o agendamento (schedule)
        if (request.getScheduleId() != null) {
            var scheduleDomain = new ScheduleDomain();
            scheduleDomain.setId(request.getScheduleId());
            domain.setScheduleDomain(scheduleDomain);
        }

        domain.setCreatedAt(LocalDateTime.now());
        domain.setUpdatedAt(LocalDateTime.now());

        return domain;
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
                .client(UserMapper.toResumeResponseDto(domain.getUserDomain()))
                .schedule(ScheduleMapper.toResumeResponseDto(domain.getScheduleDomain()))
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
                .client(UserMapper.toJpaEntity(domain.getUserDomain()))
                .schedule(ScheduleMapper.toJpaEntity(domain.getScheduleDomain()))
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
        domain.setUserDomain(UserMapper.toDomain(jpa.getClient()));
        domain.setScheduleDomain(ScheduleMapper.toDomain(jpa.getSchedule()));
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}