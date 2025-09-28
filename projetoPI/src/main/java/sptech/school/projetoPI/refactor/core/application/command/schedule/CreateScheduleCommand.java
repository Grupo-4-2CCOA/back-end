package sptech.school.projetoPI.refactor.core.application.command.schedule;

import sptech.school.projetoPI.refactor.core.application.command.scheduleItem.CreateScheduleItemCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record CreateScheduleCommand(String status,
                                            LocalDateTime appointmentDatetime,
                                            Integer duration,
                                            String transactionHash,
                                            Integer clientId,
                                            Integer employeeId,
                                            Integer paymentTypeId,
                                            Set<CreateScheduleItemCommand> items) {
}
