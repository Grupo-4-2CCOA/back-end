package sptech.school.projetoPI.infrastructure.dto.availability;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResumeResponseDto {
    private Integer id;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
}
