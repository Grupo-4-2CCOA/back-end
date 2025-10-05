package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class UpdateRoleInvalidIdException extends InvalidRoleException {
  public UpdateRoleInvalidIdException(String message) {
    super(message);
  }
}
