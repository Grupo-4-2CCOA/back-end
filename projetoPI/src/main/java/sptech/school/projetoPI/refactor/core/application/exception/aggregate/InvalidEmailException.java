package sptech.school.projetoPI.refactor.core.application.exception.aggregate;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidEmailException extends InvalidFieldException {
  public InvalidEmailException(String message) {
    super(message);
  }
}
