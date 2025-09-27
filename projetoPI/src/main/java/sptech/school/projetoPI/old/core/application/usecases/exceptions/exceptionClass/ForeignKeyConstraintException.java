package sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
