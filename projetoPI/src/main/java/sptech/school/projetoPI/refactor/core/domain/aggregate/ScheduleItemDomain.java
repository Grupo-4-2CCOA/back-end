package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.Instant;

public class ScheduleItemDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private Double finalPrice;
  private Double discount;
  private ScheduleDomain scheduleDomain;
  private ServiceDomain serviceDomain;

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

  public Double getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(Double finalPrice) {
    this.finalPrice = finalPrice;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public ScheduleDomain getScheduleDomain() {
    return scheduleDomain;
  }

  public void setScheduleDomain(ScheduleDomain scheduleDomain) {
    this.scheduleDomain = scheduleDomain;
  }

  public ServiceDomain getServiceDomain() {
    return serviceDomain;
  }

  public void setServiceDomain(ServiceDomain serviceDomain) {
    this.serviceDomain = serviceDomain;
  }
}
