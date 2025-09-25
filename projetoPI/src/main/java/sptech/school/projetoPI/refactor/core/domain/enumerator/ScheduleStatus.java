package sptech.school.projetoPI.refactor.core.domain.enumerator;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EnumIsNotValidException;

import java.util.Arrays;

public enum ScheduleStatus {
  ACTIVE,
  COMPLETED,
  CANCELED;

  public static ScheduleStatus of(String value) {
    if(Arrays.stream(ScheduleStatus.values()).anyMatch(enumValue -> enumValue.name().equals(value))) {
      return ScheduleStatus.valueOf(value);
    }

    throw new EnumIsNotValidException(
      "Status não reconhecido"
    );
  }
}
