package sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
