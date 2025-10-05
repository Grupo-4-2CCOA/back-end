package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.response;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule.scheduleItem.response.CreateScheduleItemResponseDto;

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
