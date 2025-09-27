package sptech.school.projetoPI.old.core.application.usecases.schedule;

import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;

public class GetScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public ScheduleDomain execute(Integer id) {
        return scheduleGateway.findById(id).get();
    }

}
