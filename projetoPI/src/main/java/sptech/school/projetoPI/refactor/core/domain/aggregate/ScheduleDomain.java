package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDomain {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private LocalDateTime appointmentDatetime;
    private String transactionHash;
    private Integer duration;

    private UserDomain clientDomain;
    private EmployeeDomain employeeDomain;
    private PaymentTypeDomain paymentTypeDomain;

    private List<ScheduleItemDomain> items;

    public ScheduleDomain(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Status status, LocalDateTime appointmentDatetime, String transactionHash, Integer duration, UserDomain clientDomain, EmployeeDomain employeeDomain, PaymentTypeDomain paymentTypeDomain, List<ScheduleItemDomain> items) {
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
        this.items = items;
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

    public UserDomain getClientDomain() {
        return clientDomain;
    }

    public void setClientDomain(UserDomain clientDomain) {
        this.clientDomain = clientDomain;
    }

    public EmployeeDomain getEmployeeDomain() {
        return employeeDomain;
    }

    public void setEmployeeDomain(EmployeeDomain employeeDomain) {
        this.employeeDomain = employeeDomain;
    }

    public PaymentTypeDomain getPaymentTypeDomain() {
        return paymentTypeDomain;
    }

    public void setPaymentTypeDomain(PaymentTypeDomain paymentTypeDomain) {
        this.paymentTypeDomain = paymentTypeDomain;
    }

    public List<ScheduleItemDomain> getItems() {
        return items;
    }

    public void setItems(List<ScheduleItemDomain> items) {
        this.items = items;
    }
}
