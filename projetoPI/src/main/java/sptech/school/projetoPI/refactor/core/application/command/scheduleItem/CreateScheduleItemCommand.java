package sptech.school.projetoPI.refactor.core.application.command.scheduleItem;

public record CreateScheduleItemCommand(Double finalPrice,
                                        Double discount,
                                        Integer serviceId) {
}
