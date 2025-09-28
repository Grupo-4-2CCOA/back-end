package sptech.school.projetoPI.old.infrastructure.mappers;

import sptech.school.projetoPI.old.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.old.core.application.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.old.core.application.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.old.core.application.dto.paymentType.PaymentTypeResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.PaymentTypeJpaEntity;

public class PaymentTypeMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static PaymentTypeDomain toDomain(PaymentTypeRequestDto requestObject) {
        if(requestObject == null) return null;

        PaymentTypeDomain paymentTypeDomain = new PaymentTypeDomain();
        paymentTypeDomain.setName(requestObject.getName());
        paymentTypeDomain.setDescription(requestObject.getDescription());
        paymentTypeDomain.setActive(true);
        return paymentTypeDomain;
    }

    public static PaymentTypeDomain toDomain(Integer id) {
        if (id == null) return null;

        PaymentTypeDomain paymentTypeDomain = new PaymentTypeDomain();
        paymentTypeDomain.setId(id);
        return paymentTypeDomain;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static PaymentTypeResponseDto toResponseDto(PaymentTypeDomain domain) {
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
    public static PaymentTypeResumeResponseDto toResumeResponseDto(PaymentTypeDomain domain) {
        if(domain == null) return null;

        return PaymentTypeResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static PaymentTypeJpaEntity toJpaEntity(PaymentTypeDomain domain) {
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
    public static PaymentTypeDomain toDomain(PaymentTypeJpaEntity jpa) {
        if (jpa == null) return null;

        PaymentTypeDomain domain = new PaymentTypeDomain();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}