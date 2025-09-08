package sptech.school.projetoPI.core.application.usecase.schedule;

import sptech.school.projetoPI.core.domain.ScheduleDomain;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

public class GetScheduleByIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public ScheduleDomain execute(Integer id) {
        return scheduleGateway.findById(id).get();
    }

}
