package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateScheduleUseCase {

    private final ScheduleGateway scheduleGateway;

    public Schedule postMethod(Schedule schedule) {
        if (scheduleGateway.existsByAppointmentDatetime(schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        validateRequestBody(schedule);
        schedule.setId(null);
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        return scheduleGateway.save(schedule);
    }

}
