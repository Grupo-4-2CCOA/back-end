package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.school.projetoPI.enums.Status;

import java.time.Duration;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Defina o status")
    private Status status;

    @Column(unique = true)
    @NotBlank(message = "Insira o código Hash do pagamento")
    private String transactionHash;

    private Duration duraction;

    @ManyToOne
    @JoinColumn(name="fk_employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="fk_service")
    private Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Duration getDuraction() {
        return duraction;
    }

    public void setDuraction(Duration duraction) {
        this.duraction = duraction;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
