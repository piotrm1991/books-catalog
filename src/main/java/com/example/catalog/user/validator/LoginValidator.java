package com.example.catalog.user.validator;

import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.service.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Custom validator that checks if the provided user login does not exist in the system.
 */
@RequiredArgsConstructor
public class LoginValidator implements ConstraintValidator<LoginAlreadyExists, Object> {

  private final UserService userService;

  /**
   * Validates whether the provided user login does not already exist in the system.
   *
   * @param object   String during validation on create and UserUpdate record on validation on update.
   * @param context  The context in which the validation is performed.
   * @return True if the login does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object.getClass() == String.class) {

      return !userService.checkIfExistsByLogin((String) object);
    }
    if (object.getClass() == UserUpdate.class) {

      return Objects.equals(userService.getUserByLogin(((UserUpdate) object).login()).getId(), ((UserUpdate) object).id());
    }

    return false;
  }
}

