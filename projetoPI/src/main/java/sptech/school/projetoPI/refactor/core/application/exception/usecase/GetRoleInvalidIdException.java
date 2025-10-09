package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.InvalidRoleException;

public class GetRoleInvalidIdException extends InvalidRoleException {
  public GetRoleInvalidIdException(String message) {
    super(message);
  }
}
