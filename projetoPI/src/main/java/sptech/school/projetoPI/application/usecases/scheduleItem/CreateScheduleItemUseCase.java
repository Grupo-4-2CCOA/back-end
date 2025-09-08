package sptech.school.projetoPI.application.usecases.scheduleItem;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class CreateScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;
    private final ScheduleGateway scheduleGateway;
    private final ServiceGateway serviceGateway;

    public CreateScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway, ScheduleGateway scheduleGateway, ServiceGateway serviceGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
        this.scheduleGateway = scheduleGateway;
        this.serviceGateway = serviceGateway;
    }

    public ScheduleItem execute(ScheduleItem scheduleItem) {
        if(!scheduleGateway.existsById(scheduleItem.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(scheduleItem.getSchedule().getId())
            );
        }

        if(!serviceGateway.existsById(scheduleItem.getService().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(scheduleItem.getService().getId())
            );
        }

        scheduleItem.setId(null);
        scheduleItem.setCreatedAt(LocalDateTime.now());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return scheduleItemGateway.save(scheduleItem);
    }

}
