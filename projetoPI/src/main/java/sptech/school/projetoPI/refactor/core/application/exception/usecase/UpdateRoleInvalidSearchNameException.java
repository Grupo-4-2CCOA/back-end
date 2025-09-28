package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class UpdateRoleInvalidSearchNameException extends InvalidRoleException {
  public UpdateRoleInvalidSearchNameException(String message) {
    super(message);
  }
}
