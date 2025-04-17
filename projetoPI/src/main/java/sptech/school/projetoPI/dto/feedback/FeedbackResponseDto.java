package sptech.school.projetoPI.dto.feedback;

import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer rating;
    private String comment;
    private UserResponseDto user;
    private ScheduleResponseDto schedule;
}
