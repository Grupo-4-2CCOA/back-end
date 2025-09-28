package sptech.school.projetoPI.old.core.application.usecases.paymentType;

import sptech.school.projetoPI.old.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.old.core.gateways.PaymentTypeGateway;

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
