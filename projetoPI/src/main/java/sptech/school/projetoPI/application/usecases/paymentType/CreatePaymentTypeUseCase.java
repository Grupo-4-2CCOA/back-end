package sptech.school.projetoPI.application.usecases.paymentType;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

import java.time.LocalDateTime;

public class CreatePaymentTypeUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public CreatePaymentTypeUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentType execute(PaymentType paymentType) {
        if(paymentTypeGateway.existsByNameIgnoreCase(paymentType.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentType.getName())
            );
        }

        paymentType.setId(null);
        paymentType.setCreatedAt(LocalDateTime.now());
        paymentType.setUpdatedAt(LocalDateTime.now());
        return paymentTypeGateway.save(paymentType);
    }
}
