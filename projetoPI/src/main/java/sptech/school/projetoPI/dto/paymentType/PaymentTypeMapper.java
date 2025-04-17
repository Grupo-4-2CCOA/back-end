package sptech.school.projetoPI.dto.paymentType;

import sptech.school.projetoPI.entities.PaymentType;

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
