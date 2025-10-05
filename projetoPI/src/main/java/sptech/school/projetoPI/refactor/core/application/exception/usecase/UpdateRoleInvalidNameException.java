package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class UpdateRoleInvalidNameException extends InvalidRoleException {
  public UpdateRoleInvalidNameException(String message) {
    super(message);
  }
}
