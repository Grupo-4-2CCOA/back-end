package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

    @Range(min = 0, max = 5, message = "A avaliação deve ser de 0 à 5")
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
