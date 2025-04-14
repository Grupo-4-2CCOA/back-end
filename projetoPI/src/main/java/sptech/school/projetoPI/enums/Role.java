package sptech.school.projetoPI.enums;

import sptech.school.projetoPI.exceptions.exceptionClass.EnumDoesntExistsException;

import java.util.Arrays;

public enum Role {
    OWNER,
    CUSTOMER,
    EMPLOYEE;

    public static Role roleCheck(String role) {
        return Arrays.stream(Role.values())
                .filter(enumValue -> enumValue.name().equals(role))
                .findFirst()
                .orElseThrow(()->
                        new EnumDoesntExistsException("Cargo '%s' não é permitido".formatted(role))
                );
    }
}
