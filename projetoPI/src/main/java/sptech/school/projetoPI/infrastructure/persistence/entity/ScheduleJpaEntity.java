package sptech.school.projetoPI.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule")
public class ScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime appointmentDatetime;
    private String transactionHash;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name="fk_client")
    private UserJpaEntity client;

    @ManyToOne
    @JoinColumn(name="fk_employee")
    private UserJpaEntity employee;

    @ManyToOne
    @JoinColumn(name="fk_payment_type")
    private PaymentTypeJpaEntity paymentType;

    @OneToMany(
            mappedBy = "schedule",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ScheduleItemJpaEntity> items;
}
