package sptech.school.projetoPI.application.usecases.paymentType;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.application.usecases.user.FeedbackValidationUseCase;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

import java.time.LocalDateTime;

public class UpdatePaymentTypeByIdUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public UpdatePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentType execute(PaymentType paymentType, Integer id) {
        if(!paymentTypeGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrada".formatted(id)
            );
        }

        if(paymentTypeGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O tipo de pagamento com o ID %d está inativo".formatted(id)
            );
        }

        if(paymentTypeGateway.existsByIdNotAndNameIgnoreCase(id, paymentType.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentType.getName())
            );
        }

        paymentType.setId(id);
        paymentType.setCreatedAt(paymentTypeGateway.findById(id).get().getCreatedAt());
        paymentType.setUpdatedAt(LocalDateTime.now());
        return paymentTypeGateway.save(paymentType);
    }
}
