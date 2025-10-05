package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.Duration;

public class ServiceDomain {
  private Integer id;
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
