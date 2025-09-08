package sptech.school.projetoPI.core.domains;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;

public class Schedule {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private LocalDateTime appointmentDatetime;
    private String transactionHash;
    private Integer duration;
    private Client client;
    private Employee employee;
    private PaymentType paymentType;

    public Schedule(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Status status, LocalDateTime appointmentDatetime, String transactionHash, Integer duration, Client client, Employee employee, PaymentType paymentType) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.appointmentDatetime = appointmentDatetime;
        this.transactionHash = transactionHash;
        this.duration = duration;
        this.client = client;
        this.employee = employee;
        this.paymentType = paymentType;
    }

    public Schedule() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
