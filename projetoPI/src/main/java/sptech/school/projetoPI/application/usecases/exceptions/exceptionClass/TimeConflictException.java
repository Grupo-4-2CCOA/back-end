package sptech.school.projetoPI.application.usecases.exceptions.exceptionClass;

public class TimeConflictException extends RuntimeException {
    public TimeConflictException(String message) {
        super(message);
    }
}
