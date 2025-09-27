package sptech.school.projetoPI.old.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule_item")
public class ScheduleItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double finalPrice;
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "fk_schedule")
    private ScheduleJpaEntity schedule;

    @ManyToOne
    @JoinColumn(name = "fk_service")
    private ServiceJpaEntity service;
}
