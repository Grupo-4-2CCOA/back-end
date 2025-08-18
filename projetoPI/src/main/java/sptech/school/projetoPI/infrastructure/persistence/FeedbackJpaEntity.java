package sptech.school.projetoPI.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.domains.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name="fk_client")
    private Client client;
}
