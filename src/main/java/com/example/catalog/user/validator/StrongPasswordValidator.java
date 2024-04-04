package com.example.catalog.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom validator that checks if a password meets specific strength criteria.
 */
@Slf4j
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

  /**
   * Validates whether the provided password meets specific strength criteria.
   *
   * @param value   The password to be validated.
   * @param context The context in which the validation is performed.
   * @return True if the password meets the criteria, false otherwise.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    log.info("Checks if password is strong.");
    String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$";

    return value.matches(passwordRegex);
  }
}
