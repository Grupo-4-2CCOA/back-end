package sptech.school.projetoPI.refactor.core.application.exception.usecase;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;

public class DeleteRoleUserHasRoleException extends InvalidRoleException {
  public DeleteRoleUserHasRoleException(String message) {
    super(message);
  }
}
