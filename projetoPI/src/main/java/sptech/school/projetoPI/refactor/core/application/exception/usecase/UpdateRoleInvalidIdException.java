package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class UpdateRoleInvalidIdException extends InvalidRoleException {
  public UpdateRoleInvalidIdException(String message) {
    super(message);
  }
}
