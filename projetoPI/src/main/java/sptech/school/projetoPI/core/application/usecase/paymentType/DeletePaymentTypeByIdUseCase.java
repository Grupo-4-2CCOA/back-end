package sptech.school.projetoPI.core.application.usecase.paymentType;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domain.PaymentTypeDomain;
import sptech.school.projetoPI.core.domain.ScheduleDomain;
import sptech.school.projetoPI.core.gateway.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

import java.time.LocalDateTime;

public class DeletePaymentTypeByIdUseCase {

    private final PaymentTypeGateway paymentTypeGateway;
    private final ScheduleGateway scheduleGateway;

    public DeletePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway, ScheduleGateway scheduleGateway) {
        this.paymentTypeGateway = paymentTypeGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public void execute(Integer id) {
        if(!paymentTypeGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (paymentTypeGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O tipo de pagamento com o ID %d já está inativo".formatted(id)
            );
        }

        if(scheduleGateway.existsByPaymentTypeId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este tipo de pagamento: %s".formatted(scheduleGateway.findAllByPaymentTypeId(id)
                            .stream().map(ScheduleDomain::getId).toList())
            );
        }

        PaymentTypeDomain paymentTypeDomain = paymentTypeGateway.findById(id).get();
        paymentTypeDomain.setActive(false);
        paymentTypeDomain.setUpdatedAt(LocalDateTime.now());
        paymentTypeGateway.save(paymentTypeDomain);
    }
}
