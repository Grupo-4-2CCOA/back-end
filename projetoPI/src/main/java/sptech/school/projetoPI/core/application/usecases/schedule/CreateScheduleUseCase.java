package sptech.school.projetoPI.core.application.usecases.schedule;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;

public class CreateScheduleUseCase {

    private final ScheduleGateway scheduleGateway;
    private final ClientGateway clientGateway;
    private final EmployeeGateway employeeGateway;
    private final PaymentTypeGateway paymentTypeGateway;

    public CreateScheduleUseCase(ScheduleGateway scheduleGateway, ClientGateway clientGateway, EmployeeGateway employeeGateway, PaymentTypeGateway paymentTypeGateway) {
        this.scheduleGateway = scheduleGateway;
        this.clientGateway = clientGateway;
        this.employeeGateway = employeeGateway;
        this.paymentTypeGateway = paymentTypeGateway;
    }

    public ScheduleDomain execute(ScheduleDomain scheduleDomain) {
        if (scheduleGateway.existsByAppointmentDatetime(scheduleDomain.getAppointmentDatetime())) {
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

        scheduleDomain.setId(null);
        scheduleDomain.setCreatedAt(LocalDateTime.now());
        scheduleDomain.setUpdatedAt(LocalDateTime.now());
        return scheduleGateway.save(scheduleDomain);
    }

}
