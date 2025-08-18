package sptech.school.projetoPI.infrastructure.dto.availability;

import lombok.*;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResumeResponseDto;

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
    private EmployeeResumeResponseDto employee;
}
