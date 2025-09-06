package sptech.school.projetoPI.application.usecases.scheduleItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public ScheduleItem execute(Integer id) {
        return scheduleItemGateway.findById(id);
    }

}
