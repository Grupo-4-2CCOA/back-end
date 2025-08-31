package sptech.school.projetoPI.infrastructure.exceptions.exceptionClass;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
