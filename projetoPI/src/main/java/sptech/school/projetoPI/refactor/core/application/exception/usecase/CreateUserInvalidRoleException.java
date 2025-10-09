package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class CreateUserInvalidRoleException extends InvalidRoleException {
  public CreateUserInvalidRoleException(String message) {
    super(message);
  }
}
