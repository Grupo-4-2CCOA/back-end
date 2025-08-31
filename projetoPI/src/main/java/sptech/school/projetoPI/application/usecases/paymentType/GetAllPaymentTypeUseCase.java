package sptech.school.projetoPI.application.usecases.paymentType;

import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

import java.util.List;

public class GetAllPaymentTypeUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public GetAllPaymentTypeUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public List<PaymentType> execute() {
        return paymentTypeGateway.findAllByActiveTrue();
    }
}
