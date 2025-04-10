package sptech.school.projetoPI.exceptions.exceptionClass;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
