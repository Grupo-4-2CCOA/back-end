package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.Schedule;
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

        Schedule schedule = scheduleGateway.findById(id).get();
        schedule.setStatus(Status.CANCELED);
        schedule.setUpdatedAt(LocalDateTime.now());
        scheduleGateway.save(schedule);
    }

}
