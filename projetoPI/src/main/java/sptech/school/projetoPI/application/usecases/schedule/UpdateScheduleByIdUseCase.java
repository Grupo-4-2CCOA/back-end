package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

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

    public Schedule execute(Schedule schedule, Integer id) {
        if(!scheduleGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (scheduleGateway.existsByIdNotAndAppointmentDatetime(id, schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        if (!clientGateway.existsByIdAndActiveTrue(schedule.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(schedule.getClient().getId())
            );
        }

        if (!employeeGateway.existsByIdAndActiveTrue(schedule.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O funcionário com o ID %d não foi encontrado".formatted(schedule.getEmployee().getId())
            );
        }

        if (schedule.getPaymentType() != null && !paymentTypeGateway.existsByIdAndActiveTrue(schedule.getPaymentType().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(schedule.getPaymentType().getId())
            );
        }

        schedule.setId(id);
        schedule.setCreatedAt(scheduleGateway.findById(id).get().getCreatedAt());
        return scheduleGateway.save(schedule);
    }

}
