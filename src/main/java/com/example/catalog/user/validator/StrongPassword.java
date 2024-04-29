package com.example.catalog.user.validator;

import static com.example.catalog.util.AuthenticationMessageConstants.STRONG_PASSWORD_MESSAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom validation annotation to ensure that a password meets specific strength criteria.
 */
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {

  /**
   * The validation error message to be displayed when the password does not meet the criteria.
   *
   * @return The validation error message.
   */
  String message() default STRONG_PASSWORD_MESSAGE;

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
