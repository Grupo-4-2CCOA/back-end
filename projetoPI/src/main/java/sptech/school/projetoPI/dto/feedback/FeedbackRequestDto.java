package sptech.school.projetoPI.dto.feedback;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import sptech.school.projetoPI.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;

@Getter
@Setter
public class FeedbackRequestDto {
    @Size(max = 255, message = "Comentário muito longo")
    private String comment;

    @NotNull(message = "Preencha a avaliação")
    @Range(min = 0, max = 5, message = "A avaliação deve ser de 0 à 5")
    private Integer rating;

    @NotNull(message = "Preencha o ID do Agendamento")
    private ScheduleResponseDto schedule;

    @NotNull(message = "Preencha o ID do Usuário")
    private UserResponseDto user;
}
