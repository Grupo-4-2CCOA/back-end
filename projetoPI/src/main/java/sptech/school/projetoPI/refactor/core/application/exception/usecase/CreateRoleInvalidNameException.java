package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class CreateRoleInvalidNameException extends InvalidRoleException {
  public CreateRoleInvalidNameException(String message) {
    super(message);
  }
}
