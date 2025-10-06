package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.Instant;
import java.time.LocalDateTime;

public class AvailabilityDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private Boolean isAvailable;
  private LocalDateTime startDatetime;
  private LocalDateTime endDatetime;
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

  public Boolean getAvailable() {
    return isAvailable;
  }

  public void setAvailable(Boolean available) {
    isAvailable = available;
  }

  public LocalDateTime getStartDatetime() {
    return startDatetime;
  }

  public void setStartDatetime(LocalDateTime startDatetime) {
    this.startDatetime = startDatetime;
  }

  public LocalDateTime getEndDatetime() {
    return endDatetime;
  }

  public void setEndDatetime(LocalDateTime endDatetime) {
    this.endDatetime = endDatetime;
  }

  public UserDomain getEmployeeDomain() {
    return employeeDomain;
  }

  public void setEmployeeDomain(UserDomain employeeDomain) {
    this.employeeDomain = employeeDomain;
  }
}
