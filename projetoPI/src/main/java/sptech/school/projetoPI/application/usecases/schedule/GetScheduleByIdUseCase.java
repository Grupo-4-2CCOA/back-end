package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

public class GetScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Schedule execute(Integer id) {
        return scheduleGateway.findById(id).get();
    }

}
