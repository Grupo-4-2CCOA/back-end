package sptech.school.projetoPI.core.application.usecases.schedule;

import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

public class GetScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public ScheduleDomain execute(Integer id) {
        return scheduleGateway.findById(id).get();
    }

}
