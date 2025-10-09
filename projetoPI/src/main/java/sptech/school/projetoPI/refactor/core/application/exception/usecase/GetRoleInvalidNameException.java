package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.RoleInvalidNameException;

public class GetRoleInvalidNameException extends RoleInvalidNameException {
  public GetRoleInvalidNameException(String message) {
    super(message);
  }
}
