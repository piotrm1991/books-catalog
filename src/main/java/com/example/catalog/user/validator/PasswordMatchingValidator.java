package com.example.catalog.user.validator;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Custom validator that checks if two password fields in an object match.
 */
@Slf4j
public class PasswordMatchingValidator implements
        ConstraintValidator<PasswordMatching, Object> {

  private String passwordFieldName;
  private String confirmPasswordFieldName;

  /**
   * Initializes the validator with the password and confirmPassword field names.
   *
   * @param passwords The PasswordMatching annotation with field names.
   */
  @Override
  public void initialize(PasswordMatching passwords) {
    this.passwordFieldName = passwords.password();
    this.confirmPasswordFieldName = passwords.confirmPassword();
  }

  /**
   * Validates whether the password and confirmPassword fields in the object match.
   *
   * @param value                     The object containing password and confirmPassword fields.
   * @param constraintValidatorContext The context in which the validation is performed.
   * @return True if the password and confirmPassword match, false otherwise.
   */
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
    log.info("Validating if password and confirmation match.");

    Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(passwordFieldName);
    Object confirmPasswordValue = new BeanWrapperImpl(value)
            .getPropertyValue(confirmPasswordFieldName);

    return Objects.equals(passwordValue, confirmPasswordValue);
  }
}
