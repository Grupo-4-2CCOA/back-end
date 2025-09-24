package sptech.school.projetoPI.refactor.core.domain.field;

import sptech.school.projetoPI.refactor.core.application.exception.InvalidCepException;

public class Cep {
  private String value;

  public Cep(String value) {
    if (value == null) {
      throw new InvalidCepException("O cep não pode ser nulo.");
    }
    if (value.length() != 8) {
      throw new InvalidCepException("O cep precisa conter exatamente 8 caracteres.");
    }

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
