package com.example.catalog.user.validator;

import static com.example.catalog.util.ErrorMessagesConstants.PasswordMustMatchDefaultMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom validation annotation to ensure that two password fields match.
 */
@Constraint(validatedBy = PasswordMatchingValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatching {

  /**
   * Returns the validation error message to be displayed when the passwords do not match.
   *
   * @return The validation error message.
   */
  String message() default PasswordMustMatchDefaultMessage;

  /**
   * Returns the validation groups to which this constraint belongs.
   *
   * @return An array of validation groups.
   */
  Class<?>[] groups() default {};

  /**
   * Returns additional data to be provided when a validation error occurs.
   *
   * @return An array of payload classes.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Returns the name of the field that represents the password.
   *
   * @return The name of the password field.
   */
  String password();

  /**
   * Returns the name of the field that represents the confirmation of the password.
   *
   * @return The name of the confirmation password field.
   */
  String confirmPassword();

  /**
   * Container annotation for multiple PasswordMatching annotations.
   */
  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    /**
     * Represents an array of PasswordMatching annotations. This annotation is used as a container
     * annotation to group multiple PasswordMatching annotations together.
     *
     * @see PasswordMatching
     *
     */
    PasswordMatching[] value();
  }
}
