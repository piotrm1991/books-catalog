package com.example.catalog.user.validator;

import com.example.catalog.user.service.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided user login does not exist in the system.
 */
@RequiredArgsConstructor
public class LoginValidator implements ConstraintValidator<LoginAlreadyExists, String> {

  private final UserService userService;

  /**
   * Validates whether the provided user login does not already exist in the system.
   *
   * @param login    The login to be validated.
   * @param context  The context in which the validation is performed.
   * @return True if the login does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(String login, ConstraintValidatorContext context) {
    return !userService.checkIfExistsByLogin(login);
  }
}

