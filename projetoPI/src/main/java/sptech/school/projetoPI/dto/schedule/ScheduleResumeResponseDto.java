package sptech.school.projetoPI.dto.schedule;

import lombok.*;
import sptech.school.projetoPI.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResumeResponseDto {
    private Integer id;
    private LocalDateTime appointmentDatetime;
    private Status status;
    private UserResumeResponseDto user;
}
