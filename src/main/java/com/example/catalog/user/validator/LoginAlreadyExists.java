package com.example.catalog.user.validator;

import static com.example.catalog.util.MessagesConstants.LoginAlreadyExists;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom validation annotation to ensure that
 * the provided user login does not exist in the system.
 */
@Constraint(validatedBy = LoginValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface LoginAlreadyExists {

  /**
   * The validation error message to be displayed when the login already exists.
   *
   * @return The validation error message.
   */
  String message() default LoginAlreadyExists;

  /**
   * Specifies the validation groups to which this constraint belongs.
   *
   * @return An array of validation groups.
   */
  Class<?>[] groups() default {};

  /**
   * Specifies additional data to be provided when a validation error occurs.
   *
   * @return An array of payload classes.
   */
  Class<? extends Payload>[] payload() default {};
}
