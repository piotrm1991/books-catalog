package com.example.catalog.exception;

import java.io.Serial;

/**
 * Exception thrown when a user's authentication is required to perform a certain operation,
 * but the user is not authenticated.
 * This exception typically occurs when attempting
 * to access a protected resource without proper authentication.
 */
public class UnauthenticationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7995391136847642399L;
}
