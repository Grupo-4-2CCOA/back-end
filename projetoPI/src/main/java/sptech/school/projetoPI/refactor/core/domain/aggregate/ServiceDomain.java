package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.Duration;
import java.time.Instant;

public class ServiceDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private String name;
  private Double basePrice;
  private Duration baseDuration;
  private String description;
  private String image;
  private CategoryDomain categoryDomain;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(Double basePrice) {
    this.basePrice = basePrice;
  }

  public Duration getBaseDuration() {
    return baseDuration;
  }

  public void setBaseDuration(Duration baseDuration) {
    this.baseDuration = baseDuration;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public CategoryDomain getCategoryDomain() {
    return categoryDomain;
  }

  public void setCategoryDomain(CategoryDomain categoryDomain) {
    this.categoryDomain = categoryDomain;
  }
}
