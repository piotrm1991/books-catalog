package com.example.catalog.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides error messages constants for authentication.
 */
@UtilityClass
public class AuthenticationMessageConstants {
  public static final String PASSWORDS_MUST_MATCH_DEFAULT =
          "Passwords must match!";
  public static final String STRONG_PASSWORD_MESSAGE =
          "Must be 8 characters long and combination of uppercase letters, "
        + "lowercase letters, numbers, special characters.";
  public static final String PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED =
          "Password and Confirm Password must be matched!";
  public static final String PASSWORD_IS_REQUIRED =
          "Password is required";
  public static final String CONFIRM_PASSWORD_IS_REQUIRED =
          "Confirm Password is required";
  public static final String LOGIN_IS_REQUIRED =
          "Login is required.";
  public static final String LOGIN_SIZE_MIN_MESSAGE =
          "Login must be at least 5 characters long.";
  public static final String PASSWORD_SIZE_MIN_MESSAGE =
          "Password must be at least 8 characters long.";
  public static final String LOGIN_ALREADY_EXISTS =
          "This login already exists in the system.";
  public static final String SUCCESSFULLY_LOGGED_IN =
          "You successfully logged in!";
}
