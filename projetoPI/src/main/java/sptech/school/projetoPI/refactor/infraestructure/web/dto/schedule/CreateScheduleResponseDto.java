package sptech.school.projetoPI.refactor.infraestructure.web.dto.schedule;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.scheduleItem.CreateScheduleItemResponse;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateScheduleResponseDto(Integer id,
                                        String status,
                                        LocalDateTime appointmentDatetime,
                                        Integer duration,
                                        String transactionHash,
                                        Integer clientId,
                                        Integer employeeId,
                                        Integer paymentTypeId,
                                        Set<CreateScheduleItemResponse> items) {
}
