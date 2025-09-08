package sptech.school.projetoPI.application.usecases.scheduleItem;

import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

public class GetScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public ScheduleItem execute(Integer id) {
        return scheduleItemGateway.findById(id).get();
    }

}
