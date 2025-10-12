package sptech.school.projetoPI.core.application.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.user.client.ClientResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.user.employee.EmployeeResumeResponseDto;
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
    private UserResumeResponseDto client;
    private UserResumeResponseDto employee;
}
