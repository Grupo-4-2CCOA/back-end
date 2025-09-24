package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

import java.time.LocalDateTime;

public class UserDomain {
  private Integer id;
  private Boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String name;
  private Email email;
  private Cpf cpf;
  private Phone phone;
  private Cep cep;
  private RoleDomain roleDomain;

  public UserDomain(Integer id, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, String name, Email email, Cpf cpf, Phone phone, Cep cep, RoleDomain roleDomain) {
    this.id = id;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.name = name;
    this.email = email;
    this.cpf = cpf;
    this.phone = phone;
    this.cep = cep;
    this.roleDomain = roleDomain;
  }

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

  public String getEmail() {
    return email.getValue();
  }

  public void setEmail(String email) {
    this.email = new Email(email);
  }

  public String getCpf() {
    return cpf.getValue();
  }

  public void setCpf(String cpf) {
    this.cpf = new Cpf(cpf);
  }

  public String getPhone() {
    return phone.getValue();
  }

  public void setPhone(String phone) {
    this.phone = new Phone(phone);
  }

  public String getCep() {
    return cep.getValue();
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
