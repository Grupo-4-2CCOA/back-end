package sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
