package com.example.catalog.exception;

import java.io.Serial;

/**
 * Exception thrown when a client's request is invalid, indicating a bad request.
 * This exception is typically used to represent situations where
 * the client's input does not conform
 * to the expected format or violates the requirements of the operation being performed.
 */
public class BadRequestException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 8189896061948132647L;

  public BadRequestException(String message) {
    super(message);
  }
}
