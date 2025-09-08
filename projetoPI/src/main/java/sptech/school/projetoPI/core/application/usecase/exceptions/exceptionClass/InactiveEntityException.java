package sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass;

public class InactiveEntityException extends RuntimeException {
    public InactiveEntityException(String message) {
        super(message);
    }
}
