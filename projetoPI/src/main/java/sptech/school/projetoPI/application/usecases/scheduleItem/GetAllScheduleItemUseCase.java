package sptech.school.projetoPI.application.usecases.scheduleItem;

import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

import java.util.List;

public class GetAllScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetAllScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public List<ScheduleItem> execute() {
        return scheduleItemGateway.findAll();
    }

}
