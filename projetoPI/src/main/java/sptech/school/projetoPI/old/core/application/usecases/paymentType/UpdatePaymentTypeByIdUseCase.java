package sptech.school.projetoPI.old.core.application.usecases.paymentType;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.old.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.old.core.gateways.PaymentTypeGateway;

import java.time.LocalDateTime;

public class UpdatePaymentTypeByIdUseCase {

    private final PaymentTypeGateway paymentTypeGateway;

    public UpdatePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public PaymentTypeDomain execute(PaymentTypeDomain paymentTypeDomain, Integer id) {
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

        if(paymentTypeGateway.existsByIdNotAndNameIgnoreCase(id, paymentTypeDomain.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentTypeDomain.getName())
            );
        }

        paymentTypeDomain.setId(id);
        paymentTypeDomain.setCreatedAt(paymentTypeGateway.findById(id).get().getCreatedAt());
        paymentTypeDomain.setUpdatedAt(LocalDateTime.now());
        return paymentTypeGateway.save(paymentTypeDomain);
    }
}
