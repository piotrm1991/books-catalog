package com.example.catalog.author.validator;

import com.example.catalog.author.service.impl.AuthorService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided author name does not exist in the system.
 */
@RequiredArgsConstructor
public class AuthorNameValidator implements ConstraintValidator<NameAlreadyExists, String> {

  private final AuthorService authorService;

  /**
   * Validates whether the provided author name does not already exist in the system.
   *
   * @param name    The name to be validated.
   * @param context  The context in which the validation is performed.
   * @return True if the name does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return !authorService.checkOfNameAlreadyExists(name);
  }
}
