package com.example.catalog.publisher.validator;

import com.example.catalog.publisher.service.PublisherService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided publisher name does not exist in the system.
 */
@RequiredArgsConstructor
public class PublisherNameValidator implements
        ConstraintValidator<PublisherNameAlreadyExists, String> {

  private final PublisherService publisherService;

  /**
   * Validates whether the provided publisher name does not already exist in the system.
   *
   * @param name    The name to be validated.
   * @param context  The context in which the validation is performed.
   * @return True if the name does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return !publisherService.checkOfNameAlreadyExists(name);
  }
}
