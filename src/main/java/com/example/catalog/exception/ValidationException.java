package com.example.catalog.exception;

import java.io.Serial;

/**
 * Exception thrown when validation of input data fails,
 * indicating that the provided data does not meet the required criteria.
 * This exception is typically used to represent situations where
 * user input or data fails to pass validation checks.
 */
public class ValidationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 254695641627295816L;

  public ValidationException(String message) {
    super(message);
  }
}
