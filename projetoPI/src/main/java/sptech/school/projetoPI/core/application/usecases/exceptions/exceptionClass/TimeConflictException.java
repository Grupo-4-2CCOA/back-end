package sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass;

public class TimeConflictException extends RuntimeException {
    public TimeConflictException(String message) {
        super(message);
    }
}
