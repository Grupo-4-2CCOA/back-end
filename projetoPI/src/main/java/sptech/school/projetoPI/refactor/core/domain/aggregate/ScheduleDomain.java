package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.enumerator.ScheduleStatus;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class ScheduleDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private ScheduleStatus status;
  private LocalDateTime appointmentDatetime;
  private Duration duration;
  private String transactionHash;
  private PaymentTypeDomain paymentTypeDomain;
  private UserDomain clientDomain;
  private UserDomain employeeDomain;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ScheduleStatus getStatus() {
    return status;
  }

  public void setStatus(ScheduleStatus status) {
    this.status = status;
  }

  public LocalDateTime getAppointmentDatetime() {
    return appointmentDatetime;
  }

  public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
    this.appointmentDatetime = appointmentDatetime;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public String getTransactionHash() {
    return transactionHash;
  }

  public void setTransactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
  }

  public PaymentTypeDomain getPaymentTypeDomain() {
    return paymentTypeDomain;
  }

  public void setPaymentTypeDomain(PaymentTypeDomain paymentTypeDomain) {
    this.paymentTypeDomain = paymentTypeDomain;
  }

  public UserDomain getClientDomain() {
    return clientDomain;
  }

  public void setClientDomain(UserDomain clientDomain) {
    this.clientDomain = clientDomain;
  }

  public UserDomain getEmployeeDomain() {
    return employeeDomain;
  }

  public void setEmployeeDomain(UserDomain employeeDomain) {
    this.employeeDomain = employeeDomain;
  }
}
