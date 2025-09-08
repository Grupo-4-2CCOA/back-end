package sptech.school.projetoPI.core.application.usecase.paymentType;

import sptech.school.projetoPI.core.domain.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateway.PaymentTypeGateway;

import java.util.List;

public class GetAllPaymentTypeUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public GetAllPaymentTypeUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public List<PaymentTypeDomain> execute() {
        return paymentTypeGateway.findAllByActiveTrue();
    }
}
