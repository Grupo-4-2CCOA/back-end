package sptech.school.projetoPI.enums;

import sptech.school.projetoPI.exceptions.exceptionClass.EnumIsNotValidException;

import java.util.Arrays;

public enum HttpMethod {
    POST,
    PATCH,
    PUT,
    DELETE;

    public static HttpMethod checkValue(String value) {
        if(Arrays.stream(Entity.values()).anyMatch(enumValue -> enumValue.name().equals(value))) {
            return HttpMethod.valueOf(value);
        }

        throw new EnumIsNotValidException(
                "Método HTTP não reconhecido"
        );
    }
}
