package com.example.catalog.statustype.validator;

import com.example.catalog.statustype.service.StatusTypeService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided statusType name does not exist in the system.
 */
@RequiredArgsConstructor
public class StatusTypeNameValidator implements
        ConstraintValidator<StatusTypeNameAlreadyExists, String> {

  private final StatusTypeService statusTypeService;

  /**
   * Validates whether the provided statusType name does not already exist in the system.
   *
   * @param name    The name to be validated.
   * @param context  The context in which the validation is performed.
   * @return True if the name does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return !statusTypeService.checkOfNameAlreadyExists(name);
  }
}
