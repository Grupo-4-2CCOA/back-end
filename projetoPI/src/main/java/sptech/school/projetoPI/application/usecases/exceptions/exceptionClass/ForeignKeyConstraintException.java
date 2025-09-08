package sptech.school.projetoPI.application.usecases.exceptions.exceptionClass;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
