package sptech.school.projetoPI.core.application.dto.availability;

import lombok.*;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.user.employee.EmployeeResumeResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private UserResumeResponseDto employee;
}
