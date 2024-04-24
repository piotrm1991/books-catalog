package com.example.catalog.user.validator;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.service.UserService;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided user login does not exist in the system.
 */
@RequiredArgsConstructor
public class LoginValidator implements ConstraintValidator<LoginAlreadyExists, Object> {

  private final UserService userService;

  /**
   * Validates whether the provided user login does not already exist in the system.
   *
   * @param object   String during validation on create and
   *                 UserUpdate record on validation on update.
   * @param context  The context in which the validation is performed.
   * @return True if the login does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {

    if (object.getClass() == String.class) {

      return this.checkStringLogin((String) object);
    }
    if (object.getClass() == UserUpdate.class) {

      return this.checkUserUpdateLogin((UserUpdate) object);
    }

    return false;
  }

  /**
   * Check if login exists during user update.
   * Login is allowed to exist in currently updated user.
   *
   * @param record UserUpdate record.
   * @return boolean True if validated, False if not.
   */
  private boolean checkUserUpdateLogin(UserUpdate record) {
    User user = null;
    try {
      user = userService.getUserByLogin(record.login());
    } catch (EntityNotFoundException e) {

      return true;
    }

    return user == null || (Objects.equals(user.getId(), record.id()));
  }

  /**
   * Check if login exists during user create.
   *
   * @param login String login.
   * @return boolean True if validated, False if not.
   */
  private boolean checkStringLogin(String login) {

    return !userService.checkIfExistsByLogin(login);
  }
}

