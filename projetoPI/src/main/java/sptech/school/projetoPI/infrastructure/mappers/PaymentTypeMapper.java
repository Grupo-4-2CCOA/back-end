package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.PaymentTypeJpaEntity;

public class PaymentTypeMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static PaymentType toDomain(PaymentTypeRequestDto requestObject) {
        if(requestObject == null) return null;

        PaymentType paymentType = new PaymentType();
        paymentType.setName(requestObject.getName());
        paymentType.setDescription(requestObject.getDescription());
        paymentType.setActive(true);
        return paymentType;
    }

    public static PaymentType toDomain(Integer id) {
        if (id == null) return null;

        PaymentType paymentType = new PaymentType();
        paymentType.setId(id);
        return paymentType;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static PaymentTypeResponseDto toResponseDto(PaymentType domain) {
        if(domain == null) return null;

        return PaymentTypeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= DOMAIN -> DTO (Resumo) ========= */
    public static PaymentTypeResumeResponseDto toResumeResponseDto(PaymentType domain) {
        if(domain == null) return null;

        return PaymentTypeResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static PaymentTypeJpaEntity toJpaEntity(PaymentType domain) {
        if (domain == null) return null;

        return PaymentTypeJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= JPA -> DOMAIN ========= */
    public static PaymentType toDomain(PaymentTypeJpaEntity jpa) {
        if (jpa == null) return null;

        PaymentType domain = new PaymentType();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}