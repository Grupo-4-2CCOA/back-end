package sptech.school.projetoPI.refactor.core.domain.aggregate;

import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

import java.time.LocalDateTime;

public class UserDomain {
  private String name;
  private Email email;
  private Cpf cpf;
  private Phone phone;
  private Cep cep;
  private RoleDomain roleDomain;

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
