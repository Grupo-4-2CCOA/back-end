package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

public class UserDomain {
  private Integer id;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Cpf getCpf() {
    return cpf;
  }

  public void setCpf(Cpf cpf) {
    this.cpf = cpf;
  }

  public Phone getPhone() {
    return phone;
  }

  public void setPhone(Phone phone) {
    this.phone = phone;
  }

  public Cep getCep() {
    return cep;
  }

  public void setCep(Cep cep) {
    this.cep = cep;
  }

  public RoleDomain getRoleDomain() {
    return roleDomain;
  }

  public void setRoleDomain(RoleDomain roleDomain) {
    this.roleDomain = roleDomain;
  }
}
