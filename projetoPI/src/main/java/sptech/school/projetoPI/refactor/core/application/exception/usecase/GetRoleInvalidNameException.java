package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class GetRoleInvalidNameException extends InvalidRoleException {
  public GetRoleInvalidNameException(String message) {
    super(message);
  }
}
