package sptech.school.projetoPI.refactor.util;

import java.util.Optional;

public final class UtilValidator {
  public static Boolean stringIsNullOrBlank(String string) {
    return string == null || string.isBlank();
  }
  public static Boolean stringIsNullOrBlank(Optional<String> string) {
    return string.isEmpty() || string.get().isBlank();
  }
}
