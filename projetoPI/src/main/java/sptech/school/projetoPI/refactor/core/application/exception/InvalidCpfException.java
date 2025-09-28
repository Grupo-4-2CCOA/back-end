package sptech.school.projetoPI.refactor.core.application.exception;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidCpfException extends InvalidFieldException {
  public InvalidCpfException(String message) {
    super(message);
  }
}
