package sptech.school.projetoPI.application.usecases.exceptions.exceptionClass;

public class InactiveEntityException extends RuntimeException {
    public InactiveEntityException(String message) {
        super(message);
    }
}
