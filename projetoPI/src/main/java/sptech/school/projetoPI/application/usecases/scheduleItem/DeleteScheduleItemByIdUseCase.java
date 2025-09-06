package sptech.school.projetoPI.application.usecases.scheduleItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public void execute(Integer id) {
        if (!scheduleItemGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        scheduleItemGateway.deleteById(id);
    }

}
