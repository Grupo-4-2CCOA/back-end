package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem;

public record CreateScheduleItemRequestDto(Integer id,
                                           Double finalPrice,
                                           Double discount,
                                           Integer serviceId) {
}
