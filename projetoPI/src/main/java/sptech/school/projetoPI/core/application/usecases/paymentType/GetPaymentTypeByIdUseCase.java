package sptech.school.projetoPI.core.application.usecases.paymentType;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

public class GetPaymentTypeByIdUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public GetPaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentTypeDomain execute(Integer id) {
        return paymentTypeGateway.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }
}
