package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import sptech.school.projetoPI.enums.Status;

import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "O horário do agendamento não pode estar no passado")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="fk_appointment")
    private Appointment appointment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
