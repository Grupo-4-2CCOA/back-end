package sptech.school.projetoPI.application.usecases.scheduleItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public List<ScheduleItem> execute() {
        return scheduleItemGateway.findAll();
    }

}
