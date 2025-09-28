package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule;

import jakarta.validation.constraints.NotNull;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.scheduleItem.CreateScheduleItemRequestDto;

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
