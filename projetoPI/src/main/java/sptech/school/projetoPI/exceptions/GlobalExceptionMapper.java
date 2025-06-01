package sptech.school.projetoPI.exceptions;

import io.jsonwebtoken.MalformedJwtException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sptech.school.projetoPI.exceptions.exceptionClass.*;

@Hidden
@RestControllerAdvice
public class GlobalExceptionMapper {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(400).body("Campos inválidos: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(400).body("Falha ao processar corpo de requisição: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(400).body("Tipo de parâmetro da requisição inválida: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(EntityConflictException.class)
    public static ResponseEntity<String> handleEntityConflictException(EntityConflictException exception) {
        return ResponseEntity.status(409).body("Esta entidade já existe: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public static ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(404).body("Esta entidade não existe: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(ForeignKeyConstraintException.class)
    public static ResponseEntity<String> handleForeignKeyConstraintException(ForeignKeyConstraintException exception) {
        return ResponseEntity.status(409).body("Entidade já possui relacionamento: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(TimeConflictException.class)
    public static ResponseEntity<String> handleTimeConflictException(TimeConflictException exception) {
        return ResponseEntity.status(409).body("O horário indicado está conflitando com o horário de outro agendamento: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(RelatedEntityNotFoundException.class)
    public static ResponseEntity<String> handleRelatedEntityNotFoundException(RelatedEntityNotFoundException exception) {
        return ResponseEntity.status(404).body("A entidade indicada para relacionamento não existe: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(InactiveEntityException.class)
    public static ResponseEntity<String> handleInactiveEntityException(InactiveEntityException exception) {
        return ResponseEntity.status(400).body("A entidade indicada está inativa: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(EnumIsNotValidException.class)
    public static ResponseEntity<String> handleEnumIsNotValidException(EnumIsNotValidException exception) {
        return ResponseEntity.status(400).body("Valor de Enum não é válido: %s".formatted(exception.getMessage()));
    }

    @ExceptionHandler(InvalidTimeRangeException.class)
    public static ResponseEntity<String> handleInvalidTimeRangeException(InvalidTimeRangeException exception) {
        return ResponseEntity.status(400).body("O horário inicial não pode ser após o horário final: %s".formatted(exception.getMessage()));
    }
}
