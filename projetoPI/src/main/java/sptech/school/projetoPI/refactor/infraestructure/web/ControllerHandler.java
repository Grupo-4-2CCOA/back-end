package sptech.school.projetoPI.refactor.infraestructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sptech.school.projetoPI.refactor.core.application.exception.InvalidCepException;
import sptech.school.projetoPI.refactor.core.application.exception.InvalidCpfException;
import sptech.school.projetoPI.refactor.core.application.exception.InvalidEmailException;
import sptech.school.projetoPI.refactor.core.application.exception.InvalidPhoneException;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(InvalidCepException.class)
    public ResponseEntity<String> handleInvalidCep(InvalidCepException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("CEP inválido: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<String> handleInvalidCpf(InvalidCpfException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("CPF inválido: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmail(InvalidEmailException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Email inválido: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<String> handleInvalidPhone(InvalidPhoneException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Telefone inválido: " + ex.getMessage());
    }
}

