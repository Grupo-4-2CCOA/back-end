package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.CreateScheduleItemResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateScheduleResponseDto(String status,
                                        LocalDateTime appointmentDatetime,
                                        Integer duration,
                                        String transactionHash,
                                        Integer clientId,
                                        Integer employeeId,
                                        Integer paymentTypeId,
                                        Set<CreateScheduleItemResponseDto> items) {
}
