package sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass;

public class InactiveEntityException extends RuntimeException {
    public InactiveEntityException(String message) {
        super(message);
    }
}
