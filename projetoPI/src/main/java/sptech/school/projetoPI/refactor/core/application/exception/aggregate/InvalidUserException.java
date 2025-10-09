package sptech.school.projetoPI.refactor.core.application.exception.aggregate;

public class InvalidUserException extends RuntimeException {
  public InvalidUserException(String message) {
    super(message);
  }
}
