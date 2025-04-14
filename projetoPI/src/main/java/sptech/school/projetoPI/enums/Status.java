package sptech.school.projetoPI.enums;

import sptech.school.projetoPI.exceptions.exceptionClass.EnumDoesntExistsException;

import java.util.Arrays;

public enum Status {
    ACTIVE,
    COMPLETED,
    CANCELED;

    public static Status statusCheck(String status) {
        return Arrays.stream(Status.values())
                .filter(enumValue -> enumValue.name().equals(status))
                .findFirst()
                .orElseThrow(()->
                        new EnumDoesntExistsException("Status '%s' não é permitido".formatted(status))
                );
    }
}
