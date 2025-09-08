package sptech.school.projetoPI.core.application.usecases.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
