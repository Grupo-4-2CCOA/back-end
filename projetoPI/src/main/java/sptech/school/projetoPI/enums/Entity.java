package sptech.school.projetoPI.enums;

import sptech.school.projetoPI.exceptions.exceptionClass.EnumIsNotValidException;

import java.util.Arrays;

public enum Entity {
    AVAILABILITY,
    CATEGORY,
    CLIENT,
    EMPLOYEE,
    FEEDBACK,
    PAYMENT_TYPE,
    ROLE,
    SCHEDULE,
    SCHEDULE_ITEM,
    SERVICE,
    UNAVAILABLE;

    public static Entity checkValue(String value) {
        if(Arrays.stream(Entity.values()).anyMatch(enumValue -> enumValue.name().equals(value))) {
            return Entity.valueOf(value);
        }

        throw new EnumIsNotValidException(
                "Entidade não reconhecida"
        );
    }
}
