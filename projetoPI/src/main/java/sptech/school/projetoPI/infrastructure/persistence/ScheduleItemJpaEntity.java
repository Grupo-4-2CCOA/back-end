package sptech.school.projetoPI.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.domains.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "fk_service")
    private Service service;
}
