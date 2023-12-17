package com.example.catalog.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler provides centralized handling of exceptions in the application.
 * It uses Spring's RestControllerAdvice annotation to intercept and handle exceptions thrown from
 * various controller methods.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles AccessDeniedException, indicating that the user
   * is not authorized to access a specific resource.
   *
   * @param ex The AccessDeniedException that was thrown.
   * @return A ResponseEntity with a forbidden status and an error message.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.error(AccessDeniedException.class.getName() + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("You are not authorize to see this content.",
        HttpStatus.FORBIDDEN);
  }

  /**
   * Handles BadRequestException and ValidationException,
   * indicating malformed or invalid client requests.
   *
   * @param ex The exception that was thrown.
   * @return A ResponseEntity with a bad request status and an error message.
   */
  @ExceptionHandler({
      BadRequestException.class,
      ValidationException.class
  })
  public ResponseEntity<String> handleBadRequestException(
      Exception ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler method to handle MethodArgumentNotValidException,
   * which occurs when validation
   * of method arguments annotated with @Valid fails.
   *
   * @param ex      The MethodArgumentNotValidException instance.
   * @param request The HttpServletRequest associated with the request.
   * @return A ResponseEntity containing a Map of validation errors and an HTTP status code of
   *         HttpStatus.BAD_REQUEST.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> errors = new ArrayList<>();

    ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

    Map<String, List<String>> result = new HashMap<>();
    result.put("errors", errors);

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles EntityNotFoundException, indicating that a requested entity was not found.
   *
   * @param ex The EntityNotFoundException that was thrown.
   * @return A ResponseEntity with a not found status and an error message.
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles UnauthenticationException,
   * indicating that the user is not authenticated for a specific operation.
   *
   * @param ex The UnauthenticationException that was thrown.
   * @return A ResponseEntity with an unauthorized status and an error message.
   */
  @ExceptionHandler(UnauthenticationException.class)
  public ResponseEntity<String> handleUnauthenticationException(
      UnauthenticationException ex) {
    log.error(UnauthenticationException.class.getName()
        + " status: " + HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>("You must log in to your account.",
        HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles all other unhandled exceptions that might occur in the application.
   *
   * @param ex The exception that was thrown.
   * @return A ResponseEntity with an internal server error status and an error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(
      Exception ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
