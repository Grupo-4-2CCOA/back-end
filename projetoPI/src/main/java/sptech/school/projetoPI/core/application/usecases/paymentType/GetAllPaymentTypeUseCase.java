package sptech.school.projetoPI.core.application.usecases.paymentType;

import sptech.school.projetoPI.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

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
