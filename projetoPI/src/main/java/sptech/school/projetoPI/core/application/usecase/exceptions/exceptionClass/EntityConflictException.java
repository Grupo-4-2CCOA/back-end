package sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
