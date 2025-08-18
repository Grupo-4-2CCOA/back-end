package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeRequestDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.paymentType.PaymentTypeResumeResponseDto;

public class PaymentTypeMapper {
    public static PaymentType toEntity(PaymentTypeRequestDto requestObject) {
        if(requestObject == null) return null;

        return PaymentType.builder()
                .name(requestObject.getName())
                .description(requestObject.getDescription())
                .active(true)
                .build();
    }

    public static PaymentTypeResponseDto toResponseDto(PaymentType entity) {
        if(entity == null) return null;

        PaymentTypeResponseDto paymentType = new PaymentTypeResponseDto();

        paymentType.setId(entity.getId());
        paymentType.setName(entity.getName());
        paymentType.setDescription(entity.getDescription());
        paymentType.setCreatedAt(entity.getCreatedAt());
        paymentType.setUpdatedAt(entity.getUpdatedAt());

        return paymentType;
    }

    public static PaymentTypeResumeResponseDto toResumeResponseDto(PaymentType entity) {
        if(entity == null) return null;

        return PaymentTypeResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
