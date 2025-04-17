package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name="fk_schedule")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name="fk_user")
    private User user;
}
