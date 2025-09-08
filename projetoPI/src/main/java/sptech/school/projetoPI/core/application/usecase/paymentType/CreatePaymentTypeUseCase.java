package sptech.school.projetoPI.core.application.usecase.paymentType;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domain.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateway.PaymentTypeGateway;

import java.time.LocalDateTime;

public class CreatePaymentTypeUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public CreatePaymentTypeUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentTypeDomain execute(PaymentTypeDomain paymentTypeDomain) {
        if(paymentTypeGateway.existsByNameIgnoreCase(paymentTypeDomain.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentTypeDomain.getName())
            );
        }

        paymentTypeDomain.setId(null);
        paymentTypeDomain.setCreatedAt(LocalDateTime.now());
        paymentTypeDomain.setUpdatedAt(LocalDateTime.now());
        return paymentTypeGateway.save(paymentTypeDomain);
    }
}
