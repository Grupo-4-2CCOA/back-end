package sptech.school.projetoPI.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class FeedbackJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name="fk_schedule")
    private ScheduleJpaEntity schedule;

    @ManyToOne
    @JoinColumn(name="fk_client")
    private ClientJpaEntity client;
}
