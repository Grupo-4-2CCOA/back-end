package sptech.school.projetoPI.core.application.command.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.command.client.ClientResumeResponseDto;
import sptech.school.projetoPI.core.application.command.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResumeResponseDto {
    private Integer id;
    private LocalDateTime appointmentDatetime;
    private Status status;
    private ClientResumeResponseDto client;
    private EmployeeResumeResponseDto employee;
}
