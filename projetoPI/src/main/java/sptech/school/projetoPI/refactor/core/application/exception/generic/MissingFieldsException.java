package sptech.school.projetoPI.refactor.core.application.exception.generic;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class MissingFieldsException extends InvalidRoleException {
  public MissingFieldsException(String message) {
    super(message);
  }
}
