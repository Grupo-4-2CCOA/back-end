package sptech.school.projetoPI.core.application.usecase.scheduleItem;

import sptech.school.projetoPI.core.domain.ScheduleItemDomain;
import sptech.school.projetoPI.core.gateway.ScheduleItemGateway;

public class GetScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public ScheduleItemDomain execute(Integer id) {
        return scheduleItemGateway.findById(id).get();
    }

}
