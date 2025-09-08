package sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
