package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

import java.time.Instant;

public class UserDomain {
  private Integer id;
  private Boolean isActive;
  private Instant createdAt;
  private Instant updatedAt;
  private String name;
  private Email email;
  private Cpf cpf;
  private Phone phone;
  private Cep cep;
  private RoleDomain roleDomain;

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

  public String getEmail() {
    return this.email.getValue();
  }

  public void setEmail(String email) {
    this.email = new Email(email);
  }

  public String getCpf() {
    return this.cpf.getValue();
  }

  public void setCpf(String cpf) {
    this.cpf = new Cpf(cpf);
  }

  public String getPhone() {
    return this.phone.getValue();
  }

  public void setPhone(String phone) {
    this.phone = new Phone(phone);
  }

  public String getCep() {
    return this.cep.getValue();
  }

  public void setCep(String cep) {
    this.cep = new Cep(cep);
  }

  public RoleDomain getRoleDomain() {
    return roleDomain;
  }

  public void setRoleDomain(RoleDomain roleDomain) {
    this.roleDomain = roleDomain;
  }
}
