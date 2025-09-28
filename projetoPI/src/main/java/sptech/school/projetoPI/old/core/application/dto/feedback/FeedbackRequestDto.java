package sptech.school.projetoPI.old.core.application.dto.feedback;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDto {
    @Size(max = 255, message = "Comentário muito longo")
    private String comment;

    @NotNull(message = "Preencha a avaliação")
    @Range(min = 0, max = 5, message = "A avaliação deve ser de 0 à 5")
    private Integer rating;

    @Positive(message = "ID inválido para Agendamento")
    @NotNull(message = "Preencha o ID do Agendamento")
    private Integer schedule;

    @Positive(message = "ID inválido para Cliente")
    @NotNull(message = "Preencha o ID do Cliente")
    private Integer client;
}
