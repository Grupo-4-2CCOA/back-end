package sptech.school.projetoPI.exceptions;

public class EnumDoesntExistsException extends RuntimeException {
    public EnumDoesntExistsException(String message) {
        super(message);
    }
}
