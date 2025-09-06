package sptech.school.projetoPI.application.usecases.scheduleItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public ScheduleItem execute(ScheduleItem scheduleItem) {
        validateRequestBody(scheduleItem);
        scheduleItem.setId(null);
        scheduleItem.setCreatedAt(LocalDateTime.now());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return scheduleItemGateway.save(scheduleItem);
    }

}
