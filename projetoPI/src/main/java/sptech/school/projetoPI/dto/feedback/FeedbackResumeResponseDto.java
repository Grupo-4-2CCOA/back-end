package sptech.school.projetoPI.dto.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResumeResponseDto {
    private Integer id;
    private String comment;
    private Integer rating;
}
