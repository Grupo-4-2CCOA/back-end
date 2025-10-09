package sptech.school.projetoPI.refactor.core.domain.field;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidCpfException;

public class Cpf {
  private String value;

  public Cpf(String value) {
    if (value == null) {
      throw new InvalidCpfException("O cpf não pode ser nulo.");
    }
    if (value.length() != 11) {
      throw new InvalidCpfException("O cpf precisa conter exatamente 11 caracteres.");
    }

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
