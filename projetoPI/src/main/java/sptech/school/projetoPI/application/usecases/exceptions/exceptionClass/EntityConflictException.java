package sptech.school.projetoPI.application.usecases.exceptions.exceptionClass;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
