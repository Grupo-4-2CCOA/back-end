package sptech.school.projetoPI.core.domains;

import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;

public class ScheduleDomain {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private LocalDateTime appointmentDatetime;
    private String transactionHash;
    private Integer duration;
    private ClientDomain clientDomain;
    private EmployeeDomain employeeDomain;
    private PaymentTypeDomain paymentTypeDomain;

    public ScheduleDomain(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Status status, LocalDateTime appointmentDatetime, String transactionHash, Integer duration, ClientDomain clientDomain, EmployeeDomain employeeDomain, PaymentTypeDomain paymentTypeDomain) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.appointmentDatetime = appointmentDatetime;
        this.transactionHash = transactionHash;
        this.duration = duration;
        this.clientDomain = clientDomain;
        this.employeeDomain = employeeDomain;
        this.paymentTypeDomain = paymentTypeDomain;
    }

    public ScheduleDomain() {

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

    public ClientDomain getClient() {
        return clientDomain;
    }

    public void setClient(ClientDomain clientDomain) {
        this.clientDomain = clientDomain;
    }

    public EmployeeDomain getEmployee() {
        return employeeDomain;
    }

    public void setEmployee(EmployeeDomain employeeDomain) {
        this.employeeDomain = employeeDomain;
    }

    public PaymentTypeDomain getPaymentType() {
        return paymentTypeDomain;
    }

    public void setPaymentType(PaymentTypeDomain paymentTypeDomain) {
        this.paymentTypeDomain = paymentTypeDomain;
    }
}
