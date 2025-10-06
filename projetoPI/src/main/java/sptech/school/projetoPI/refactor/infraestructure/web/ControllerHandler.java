package sptech.school.projetoPI.refactor.infraestructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sptech.school.projetoPI.refactor.core.application.exception.*;
import sptech.school.projetoPI.refactor.core.application.exception.generic.InvalidRoleException;
import sptech.school.projetoPI.refactor.core.application.exception.generic.MissingFieldsException;
import sptech.school.projetoPI.refactor.core.application.exception.generic.NotFoundException;

// TODO: criar códigos para cada resposta.
@ControllerAdvice
public class ControllerHandler {

  @ExceptionHandler(InvalidCepException.class)
  public ResponseEntity<String> handleInvalidCep(InvalidCepException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("CEP inválido: " + exception.getMessage());
  }

  @ExceptionHandler(InvalidCpfException.class)
  public ResponseEntity<String> handleInvalidCpf(InvalidCpfException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("CPF inválido: " + exception.getMessage());
  }

  @ExceptionHandler(InvalidEmailException.class)
  public ResponseEntity<String> handleInvalidEmail(InvalidEmailException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("Email inválido: " + exception.getMessage());
  }

  @ExceptionHandler(InvalidPhoneException.class)
  public ResponseEntity<String> handleInvalidPhone(InvalidPhoneException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("Telefone inválido: " + exception.getMessage());
  }

  @ExceptionHandler(InvalidRoleException.class)
  public ResponseEntity<String> handleInvalidRole(InvalidRoleException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("Cargo inválido: " + exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleUpdateNothingModified(NotFoundException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("Não encontrado: " + exception.getMessage());
  }

  @ExceptionHandler(MissingFieldsException.class)
  public ResponseEntity<String> handleUpdateNothingModified(MissingFieldsException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body("Faltando campo: " + exception.getMessage());
  }
}
