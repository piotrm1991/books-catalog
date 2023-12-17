package com.example.catalog.exception;

import java.io.Serial;

/**
 * Exception thrown when an entity, such as a record or an object,
 * is not found in a data store or system.
 * This exception is typically used to indicate that a requested
 * entity could not be located based on the given criteria.
 */
public class EntityNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2091208377579749890L;

  public EntityNotFoundException(String message) {
    super(message);
  }
}
