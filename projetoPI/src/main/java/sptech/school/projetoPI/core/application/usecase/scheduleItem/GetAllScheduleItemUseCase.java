package sptech.school.projetoPI.core.application.usecase.scheduleItem;

import sptech.school.projetoPI.core.domain.ScheduleItemDomain;
import sptech.school.projetoPI.core.gateway.ScheduleItemGateway;

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
