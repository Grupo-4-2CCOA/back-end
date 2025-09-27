package sptech.school.projetoPI.old.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class UpdateScheduleItemByIdUseCase {

    private final ScheduleItemGateway scheduleItemGateway;
    private final ScheduleGateway scheduleGateway;
    private final ServiceGateway serviceGateway;

    public UpdateScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway, ScheduleGateway scheduleGateway, ServiceGateway serviceGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
        this.scheduleGateway = scheduleGateway;
        this.serviceGateway = serviceGateway;
    }

    public ScheduleItemDomain execute(ScheduleItemDomain scheduleItemDomain, Integer id) {
        if (!scheduleItemGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(!scheduleGateway.existsById(scheduleItemDomain.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(scheduleItemDomain.getSchedule().getId())
            );
        }

        if(!serviceGateway.existsById(scheduleItemDomain.getService().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(scheduleItemDomain.getService().getId())
            );
        }

        scheduleItemDomain.setId(id);
        scheduleItemDomain.setCreatedAt(scheduleItemGateway.findById(id).get().getCreatedAt());
        scheduleItemDomain.setUpdatedAt(LocalDateTime.now());
        return scheduleItemGateway.save(scheduleItemDomain);
    }

}
