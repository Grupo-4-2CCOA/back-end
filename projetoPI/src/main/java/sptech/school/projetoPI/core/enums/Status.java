package sptech.school.projetoPI.core.enums;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EnumIsNotValidException;

import java.util.Arrays;

public enum Status {
    ACTIVE,
    COMPLETED,
    CANCELED;

    public static Status checkValue(String value) {
        if(Arrays.stream(Status.values()).anyMatch(enumValue -> enumValue.name().equals(value))) {
            return Status.valueOf(value);
        }

        throw new EnumIsNotValidException(
                "Status não reconhecido"
        );
    }
}
