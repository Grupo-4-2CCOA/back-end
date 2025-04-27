package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule {
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
    private Client client;

    @ManyToOne
    @JoinColumn(name="fk_employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="fk_payment_type")
    private PaymentType paymentType;
}
