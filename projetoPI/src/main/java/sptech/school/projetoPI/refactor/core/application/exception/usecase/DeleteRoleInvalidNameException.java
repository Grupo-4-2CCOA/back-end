package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class DeleteRoleInvalidNameException extends InvalidRoleException {
  public DeleteRoleInvalidNameException(String message) {
    super(message);
  }
}
