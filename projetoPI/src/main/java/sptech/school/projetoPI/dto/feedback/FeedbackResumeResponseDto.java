package sptech.school.projetoPI.dto.feedback;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResumeResponseDto {
    private Integer id;
    private String comment;
    private Integer rating;
}
