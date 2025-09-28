package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem;

public record CreateScheduleItemResponseDto(Double finalPrice,
                                            Double discount,
                                            Integer serviceId) {
}
