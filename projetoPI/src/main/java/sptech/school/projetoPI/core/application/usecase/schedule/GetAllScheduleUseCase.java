package sptech.school.projetoPI.core.application.usecase.schedule;

import sptech.school.projetoPI.core.domain.ScheduleDomain;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

import java.util.List;

public class GetAllScheduleUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetAllScheduleUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public List<ScheduleDomain> execute() {
        return scheduleGateway.findAll();
    }

}
