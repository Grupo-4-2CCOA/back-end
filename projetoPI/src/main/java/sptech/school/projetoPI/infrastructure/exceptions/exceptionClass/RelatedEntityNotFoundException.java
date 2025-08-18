package sptech.school.projetoPI.infrastructure.exceptions.exceptionClass;

public class RelatedEntityNotFoundException extends RuntimeException {
    public RelatedEntityNotFoundException(String message) {
        super(message);
    }
}
