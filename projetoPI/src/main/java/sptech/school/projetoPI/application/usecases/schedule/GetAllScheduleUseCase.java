package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllScheduleUseCase {

    private final ScheduleGateway scheduleGateway;


    public List<Schedule> execute() {
        return scheduleGateway.findAll();
    }

}
