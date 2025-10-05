package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.request;

import jakarta.validation.constraints.NotNull;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.request.CreateScheduleItemRequestDto;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateScheduleRequestDto(@NotNull String status,
                                       @NotNull LocalDateTime appointmentDatetime,
                                       Integer duration,
                                       String transactionHash,
                                       @NotNull Integer clientId,
                                       @NotNull Integer employeeId,
                                       Integer paymentTypeId,
                                       @NotNull Set<CreateScheduleItemRequestDto> items) {
}
