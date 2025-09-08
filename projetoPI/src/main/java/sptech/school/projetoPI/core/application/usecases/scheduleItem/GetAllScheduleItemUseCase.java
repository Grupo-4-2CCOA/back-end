package sptech.school.projetoPI.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

import java.util.List;

public class GetAllScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public GetAllScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public List<ScheduleItemDomain> execute() {
        return scheduleItemGateway.findAll();
    }

}
