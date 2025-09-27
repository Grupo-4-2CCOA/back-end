package sptech.school.projetoPI.old.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;

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
