package sptech.school.projetoPI.dto.feedback;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class FeedbackRequestDto {
    @Size(max = 255, message = "Comentário muito longo")
    private String comment;

    @NotNull(message = "Preencha a avaliação")
    @Range(min = 0, max = 5, message = "A avaliação deve ser de 0 à 5")
    private Integer rating;

    @NotNull(message = "Preencha o ID do Agendamento")
    @Positive(message = "ID inválido para Agendamento")
    private Integer schedule;

    @NotNull(message = "Preencha o ID do Usuário")
    @Positive(message = "ID inválido para Usuário")
    private Integer user;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating (Integer rating) {
        this.rating = rating;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
