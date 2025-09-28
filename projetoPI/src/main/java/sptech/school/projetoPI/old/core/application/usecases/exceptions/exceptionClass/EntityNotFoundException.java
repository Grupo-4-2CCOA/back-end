package sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
