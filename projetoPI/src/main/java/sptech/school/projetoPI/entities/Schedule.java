package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
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
    @JoinColumn(name="fk_user")
    private User user;

    @ManyToOne
    @JoinColumn(name="fk_payment_type")
    private PaymentType paymentType;
}
