package com.example.catalog.exception;

import java.io.Serial;

/**
 * This exception is thrown when access to a certain resource or operation is denied.
 * This typically occurs when a user does not have the necessary
 * permissions to perform the requested action.
 */
public class AccessDeniedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2416665549067346574L;
}
