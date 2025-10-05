package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.LocalDateTime;

public class AvailabilityDomain {
  private Integer id;
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
