package sptech.school.projetoPI.dto.paymentType;

import sptech.school.projetoPI.entities.PaymentType;

public class PaymentTypeMapper {
    public static PaymentType toEntity(PaymentTypeRequestDto requestObject) {
        PaymentType paymentType = new PaymentType();

        paymentType.setName(requestObject.getName());
        paymentType.setDescription(requestObject.getDescription());
        paymentType.setActive(true);

        return paymentType;
    }

    public static PaymentType toEntity(PaymentTypeResponseDto responseObject) {
        PaymentType paymentType = new PaymentType();
        paymentType.setId(responseObject.getId());
        return paymentType;
    }

    public static PaymentTypeResponseDto toResponseDto(PaymentType entity) {
        PaymentTypeResponseDto paymentType = new PaymentTypeResponseDto();

        paymentType.setId(entity.getId());
        paymentType.setName(entity.getName());
        paymentType.setDescription(entity.getDescription());
        paymentType.setCreatedAt(entity.getCreatedAt());
        paymentType.setUpdatedAt(entity.getUpdatedAt());

        return paymentType;
    }
}
