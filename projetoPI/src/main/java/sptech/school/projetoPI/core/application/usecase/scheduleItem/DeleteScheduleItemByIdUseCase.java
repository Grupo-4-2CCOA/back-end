package sptech.school.projetoPI.core.application.usecase.scheduleItem;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.gateway.ScheduleItemGateway;

public class DeleteScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;

    public DeleteScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
    }

    public void execute(Integer id) {
        if (!scheduleItemGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        scheduleItemGateway.deleteById(id);
    }

}
