package sptech.school.projetoPI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate day;
    private LocalTime time;
    private Duration duration;
    private User user;
    private Service servico;
    private Employee funcionario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public Service getServico() {
        return servico;
    }

    public void setServico(Service servico) {
        this.servico = servico;
    }

    public Employee getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Employee funcionario) {
        this.funcionario = funcionario;
    }
}
