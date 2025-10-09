package sptech.school.projetoPI.refactor.core.application.exception.aggregate;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidCpfException extends InvalidFieldException {
  public InvalidCpfException(String message) {
    super(message);
  }
}
