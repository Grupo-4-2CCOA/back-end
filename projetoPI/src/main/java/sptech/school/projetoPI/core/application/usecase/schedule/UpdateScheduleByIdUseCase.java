package sptech.school.projetoPI.core.application.usecase.schedule;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domain.ScheduleDomain;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;
import sptech.school.projetoPI.core.gateway.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

public class UpdateScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;
    private final ClientGateway clientGateway;
    private final EmployeeGateway employeeGateway;
    private final PaymentTypeGateway paymentTypeGateway;

    public UpdateScheduleByIdUseCase(ScheduleGateway scheduleGateway, ClientGateway clientGateway, EmployeeGateway employeeGateway, PaymentTypeGateway paymentTypeGateway) {
        this.scheduleGateway = scheduleGateway;
        this.clientGateway = clientGateway;
        this.employeeGateway = employeeGateway;
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

        if (!clientGateway.existsByIdAndActiveTrue(scheduleDomain.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(scheduleDomain.getClient().getId())
            );
        }

        if (!employeeGateway.existsByIdAndActiveTrue(scheduleDomain.getEmployee().getId())) {
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
