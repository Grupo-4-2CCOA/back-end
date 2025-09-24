package sptech.school.projetoPI.refactor.core.domain.field;

import sptech.school.projetoPI.refactor.core.application.exception.InvalidPhoneException;

public class Phone {
  private String value;

  public Phone(String value) {
    if (value == null) {
      throw new InvalidPhoneException("O telefone não pode ser nulo.");
    }
    if (value.length() != 13) {
      throw new InvalidPhoneException("O telefone precisa conter exatamente 13 caracteres.");
    }

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
