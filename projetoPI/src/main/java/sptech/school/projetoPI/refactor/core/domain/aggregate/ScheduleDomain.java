package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.enumerator.ScheduleStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class ScheduleDomain {
  private Integer id;
  private ScheduleStatus status;
  private LocalDateTime appointmentDatetime;
  private Duration duration;
  private String transactionHash;
  private PaymentTypeDomain paymentTypeDomain;
  private UserDomain clientDomain;
  private UserDomain employeeDomain;

    public ScheduleDomain(Integer id, ScheduleStatus status, LocalDateTime appointmentDatetime, Duration duration, String transactionHash, PaymentTypeDomain paymentTypeDomain, UserDomain clientDomain, UserDomain employeeDomain) {
        this.id = id;
        this.status = status;
        this.appointmentDatetime = appointmentDatetime;
        this.duration = duration;
        this.transactionHash = transactionHash;
        this.paymentTypeDomain = paymentTypeDomain;
        this.clientDomain = clientDomain;
        this.employeeDomain = employeeDomain;
    }

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
