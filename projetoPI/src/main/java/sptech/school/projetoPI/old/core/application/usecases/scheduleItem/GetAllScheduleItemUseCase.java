package sptech.school.projetoPI.old.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;

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
