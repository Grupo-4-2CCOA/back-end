//package sptech.school.projetoPI.refactor.core.application.usecase.schedule;
//
//import sptech.school.projetoPI.old.core.enums.Status;
//import sptech.school.projetoPI.refactor.core.application.command.schedule.CreateScheduleCommand;
//import sptech.school.projetoPI.refactor.core.application.command.scheduleItem.CreateScheduleItemCommand;
//import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleDomain;
//import sptech.school.projetoPI.refactor.core.domain.aggregate.ScheduleItemDomain;
//import sptech.school.projetoPI.refactor.core.gateway.ScheduleGateway;
//import sptech.school.projetoPI.refactor.core.gateway.ScheduleItemGateway;
//
//public class CreateScheduleUseCase {
//
//    private final ScheduleGateway scheduleGateway;
//    private final ScheduleItemGateway scheduleItemGateway;
//
//    public CreateScheduleUseCase(ScheduleGateway scheduleGateway, ScheduleItemGateway scheduleItemGateway) {
//        this.scheduleGateway = scheduleGateway;
//        this.scheduleItemGateway = scheduleItemGateway;
//    }
//
//
//    public ScheduleDomain execute(CreateScheduleCommand command) {
//        if (command == null || command.items() == null || command.items().isEmpty()) {
//            throw new IllegalArgumentException("Schedule e itens não podem ser nulos ou vazios.");
//        }
//
//        // cria o ScheduleDomain
//        ScheduleDomain schedule = new ScheduleDomain();
//        schedule.setStatus(Status.ACTIVE);
//        schedule.setAppointmentDatetime(command.appointmentDatetime());
//        schedule.setDuration(command.duration());
//        schedule.setTransactionHash(command.transactionHash());
//
//        // salva o Schedule
//        ScheduleDomain savedSchedule = scheduleGateway.save(schedule);
//
//        // cria os itens relacionados
//        for (CreateScheduleItemCommand itemCommand : command.items()) {
//            ScheduleItemDomain item = new ScheduleItemDomain();
//            item.setFinalPrice(itemCommand.finalPrice());
//            item.setDiscount(itemCommand.discount());
//            item.setServiceId(itemCommand.serviceId());
//            item.setScheduleId(savedSchedule.getId());
//
//            scheduleItemGateway.save(item);
//        }
//
//        return savedSchedule;
//    }
//}
