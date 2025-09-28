package sptech.school.projetoPI.old.core.application.usecases.scheduleItem;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.domains.ServiceDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Optional;

public class CreateScheduleItemUseCase {

    private final ScheduleItemGateway scheduleItemGateway;
    private final ScheduleGateway scheduleGateway;
    private final ServiceGateway serviceGateway;

    public CreateScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway, ScheduleGateway scheduleGateway, ServiceGateway serviceGateway) {
        this.scheduleItemGateway = scheduleItemGateway;
        this.scheduleGateway = scheduleGateway;
        this.serviceGateway = serviceGateway;
    }

    public ScheduleItemDomain execute(ScheduleItemDomain scheduleItemDomain) {
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

        Optional<ServiceDomain> service = serviceGateway.findById(scheduleItemDomain.getService().getId());
        scheduleItemDomain.setId(null);
        scheduleItemDomain.setService(service.get());
        scheduleItemDomain.setCreatedAt(LocalDateTime.now());
        scheduleItemDomain.setUpdatedAt(LocalDateTime.now());
        return scheduleItemGateway.save(scheduleItemDomain);
    }
}
