package sptech.school.projetoPI.refactor.core.application.exception.aggregate;

import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidFieldException;

public class InvalidScheduleStatusException extends InvalidFieldException {
  public InvalidScheduleStatusException(String message) {
    super(message);
  }
}
