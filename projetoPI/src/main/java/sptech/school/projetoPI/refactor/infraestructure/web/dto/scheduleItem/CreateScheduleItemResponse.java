package sptech.school.projetoPI.refactor.infraestructure.web.dto.scheduleItem;

public record CreateScheduleItemResponse(Integer id,
                                         Double finalPrice,
                                         Double discount,
                                         Integer serviceId) {
}
