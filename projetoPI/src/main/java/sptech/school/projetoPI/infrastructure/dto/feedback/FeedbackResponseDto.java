package sptech.school.projetoPI.infrastructure.dto.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.infrastructure.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;

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
    private ClientResumeResponseDto client;
    private ScheduleResumeResponseDto schedule;
}
