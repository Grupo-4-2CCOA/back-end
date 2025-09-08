package sptech.school.projetoPI.core.application.usecase.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
