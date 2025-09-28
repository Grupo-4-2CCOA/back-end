package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.LocalDateTime;
import java.util.Set;

public class RoleDomain {
  private Integer id;
  private Boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String name;
  private String description;
  private Set<UserDomain> userDomains;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void addUserDomain(UserDomain userDomain) {
    this.userDomains.add(userDomain);
  }

  public Set<UserDomain> getUserDomains() {
    return userDomains;
  }

  public void setUserDomains(Set<UserDomain> userDomains) {
    this.userDomains = userDomains;
  }
}
