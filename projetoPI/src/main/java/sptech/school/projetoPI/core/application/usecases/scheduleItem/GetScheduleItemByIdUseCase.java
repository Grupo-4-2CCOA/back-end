package sptech.school.projetoPI.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

public class GetScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public ScheduleItemDomain execute(Integer id) {
        return scheduleItemGateway.findById(id).get();
    }

}
