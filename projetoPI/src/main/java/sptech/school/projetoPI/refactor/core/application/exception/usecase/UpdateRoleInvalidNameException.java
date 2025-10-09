package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.aggregate.RoleInvalidNameException;

public class UpdateRoleInvalidNameException extends RoleInvalidNameException {
  public UpdateRoleInvalidNameException(String message) {
    super(message);
  }
}
