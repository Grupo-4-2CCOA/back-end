package sptech.school.projetoPI.old.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;

public class GetScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public ScheduleItemDomain execute(Integer id) {
        return scheduleItemGateway.findById(id).get();
    }

}
