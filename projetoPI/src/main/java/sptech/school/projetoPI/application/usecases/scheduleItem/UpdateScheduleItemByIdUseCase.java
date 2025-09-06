package sptech.school.projetoPI.application.usecases.scheduleItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public ScheduleItem putByIdMethod(ScheduleItem scheduleItem, Integer id) {
        if (!scheduleItemGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        validateRequestBody(scheduleItem);
        scheduleItem.setId(id);
        scheduleItem.setCreatedAt(scheduleItemGateway.findById(id).get().getCreatedAt());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return scheduleItemGateway.save(scheduleItem);
    }

}
