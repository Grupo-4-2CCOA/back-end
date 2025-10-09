package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.RoleInvalidNameException;
import sptech.school.projetoPI.refactor.util.UtilValidator;

import java.time.Instant;

public class RoleDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private String name;
  private String description;

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
    // remove os espaços em branco no começo e no final da string:
    String trimmedName = name.trim();

    if (UtilValidator.stringIsNullOrBlank(trimmedName)) {
      throw new RoleInvalidNameException("O nome não pode estar vazio (não pode ser nulo ou estar em branco).");
    }
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    // remove os espaços em branco no começo e no final da string:
    String trimmedDescription = description.trim();

    this.description = trimmedDescription;
  }
}
