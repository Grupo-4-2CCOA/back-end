package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

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

        validateRequestBody(schedule);
        schedule.setId(id);
        schedule.setCreatedAt(scheduleGateway.findById(id).getCreatedAt());
        return scheduleGateway.save(schedule);
    }

}
