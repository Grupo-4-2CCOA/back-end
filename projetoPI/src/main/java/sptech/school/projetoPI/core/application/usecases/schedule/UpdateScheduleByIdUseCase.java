package sptech.school.projetoPI.core.application.usecases.schedule;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

public class UpdateScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;
    private final UserGateway userGateway;
    private final PaymentTypeGateway paymentTypeGateway;

    public UpdateScheduleByIdUseCase(ScheduleGateway scheduleGateway, UserGateway userGateway, PaymentTypeGateway paymentTypeGateway) {
        this.scheduleGateway = scheduleGateway;
        this.userGateway = userGateway;
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public ScheduleDomain execute(ScheduleDomain scheduleDomain, Integer id) {
        if(!scheduleGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (scheduleGateway.existsByIdNotAndAppointmentDatetime(id, scheduleDomain.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        if (!userGateway.existsByIdAndActiveTrue(scheduleDomain.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(scheduleDomain.getClient().getId())
            );
        }

        if (!userGateway.existsByIdAndActiveTrue(scheduleDomain.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O funcionário com o ID %d não foi encontrado".formatted(scheduleDomain.getEmployee().getId())
            );
        }

        if (scheduleDomain.getPaymentType() != null && !paymentTypeGateway.existsByIdAndActiveTrue(scheduleDomain.getPaymentType().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(scheduleDomain.getPaymentType().getId())
            );
        }

        scheduleDomain.setId(id);
        scheduleDomain.setCreatedAt(scheduleGateway.findById(id).get().getCreatedAt());
        return scheduleGateway.save(scheduleDomain);
    }

}
