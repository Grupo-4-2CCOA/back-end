package sptech.school.projetoPI.core.application.usecases.schedule;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;

public class DeleteScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public DeleteScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public void execute(Integer id) {
        if (!scheduleGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (scheduleGateway.existsByIdAndStatus(id, Status.CANCELED)) {
            throw new InactiveEntityException(
                    "O agendamento com o ID %d já foi cancelado".formatted(id)
            );
        }

        ScheduleDomain scheduleDomain = scheduleGateway.findById(id).get();
        scheduleDomain.setStatus(Status.CANCELED);
        scheduleDomain.setUpdatedAt(LocalDateTime.now());
        scheduleGateway.save(scheduleDomain);
    }

}
