package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import sptech.school.projetoPI.enums.PaymentType;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentType type;

    @NotNull
    private LocalDateTime dateTime;
    private String transaction;

    @ManyToOne
    @JoinColumn(name="fk_user")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
