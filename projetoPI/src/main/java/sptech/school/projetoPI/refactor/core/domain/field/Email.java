package sptech.school.projetoPI.refactor.core.domain.field;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidEmailException;

public class Email {
  private String value;

  public Email(String value) {
    if (value == null) {
      throw new InvalidEmailException("O email não pode ser nulo.");
    }
    if (!value.contains("@")) {
      throw new InvalidEmailException("O email precisa conter um arroba (\"@\").");
    }

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
