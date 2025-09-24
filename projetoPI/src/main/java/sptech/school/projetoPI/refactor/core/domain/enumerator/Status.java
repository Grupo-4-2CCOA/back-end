package sptech.school.projetoPI.refactor.core.domain.enumerator;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EnumIsNotValidException;

import java.util.Arrays;

public enum Status {
  ACTIVE,
  COMPLETED,
  CANCELED;

  public static Status of(String value) {
    if(Arrays.stream(Status.values()).anyMatch(enumValue -> enumValue.name().equals(value))) {
      return Status.valueOf(value);
    }

    throw new EnumIsNotValidException(
      "Status não reconhecido"
    );
  }
}
