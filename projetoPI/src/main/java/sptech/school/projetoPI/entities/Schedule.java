package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Preencha o horário do agendamento")
    @FutureOrPresent(message = "O horário do agendamento não pode estar no passado")
    private LocalDateTime dateTime;
    private Duration duration;

    @ManyToOne
    @JoinColumn(name="fk_user")
    private User user;

    @ManyToOne
    @JoinColumn(name="fk_service")
    private Service service;

    @ManyToOne
    @JoinColumn(name="fk_employee")
    private Employee employee;

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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
