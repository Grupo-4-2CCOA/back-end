package sptech.school.projetoPI.application.usecases.paymentType;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

public class GetPaymentTypeByIdUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public GetPaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentType execute(Integer id) {
        return paymentTypeGateway.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }
}
