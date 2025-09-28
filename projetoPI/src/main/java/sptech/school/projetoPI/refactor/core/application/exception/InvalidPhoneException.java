package sptech.school.projetoPI.refactor.core.application.exception;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidPhoneException extends InvalidFieldException {
  public InvalidPhoneException(String message) {
    super(message);
  }
}
