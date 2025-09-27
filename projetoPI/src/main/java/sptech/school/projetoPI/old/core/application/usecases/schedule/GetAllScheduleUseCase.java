package sptech.school.projetoPI.old.core.application.usecases.schedule;

import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;

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
