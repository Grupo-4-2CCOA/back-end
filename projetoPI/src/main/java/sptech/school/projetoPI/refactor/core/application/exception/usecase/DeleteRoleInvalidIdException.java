package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class DeleteRoleInvalidIdException extends InvalidRoleException {
  public DeleteRoleInvalidIdException(String message) {
    super(message);
  }
}
