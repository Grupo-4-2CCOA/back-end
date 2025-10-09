package sptech.school.projetoPI.refactor.core.application.exception.aggregate;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidCepException extends InvalidFieldException {
  public InvalidCepException(String message) {
    super(message);
  }
}
