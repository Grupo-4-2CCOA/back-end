package sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass;

public class RelatedEntityNotFoundException extends RuntimeException {
    public RelatedEntityNotFoundException(String message) {
        super(message);
    }
}
