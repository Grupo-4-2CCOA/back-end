package sptech.school.projetoPI.application.usecases.exceptions.exceptionClass;

public class EnumIsNotValidException extends RuntimeException {
    public EnumIsNotValidException(String message) {
        super(message);
    }
}
