package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class DeleteRoleInvalidIdException extends InvalidRoleException {
  public DeleteRoleInvalidIdException(String message) {
    super(message);
  }
}
