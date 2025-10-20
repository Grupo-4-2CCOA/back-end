package sptech.school.projetoPI.core.application.dto.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.user.client.ClientResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer rating;
    private String comment;
    private UserResumeResponseDto client;
    private ScheduleResumeResponseDto schedule;
}
