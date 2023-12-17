package com.example.catalog.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides error messages constants.
 */
@UtilityClass
public class ErrorMessagesConstants {

  public static final String AuthorNameAlreadyExists = "Author with this name already exists.";
  public static final String AuthorNameCanNotBeBlank = "You need to provide author name.";

  public static String createAuthorNotExistMessage(Long id) {

    return String.format("Author with id: %d is not found.", id);
  }
}
