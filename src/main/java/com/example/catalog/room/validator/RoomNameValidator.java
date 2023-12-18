package com.example.catalog.room.validator;

import com.example.catalog.room.service.RoomService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Custom validator that checks if the provided room name does not exist in the system.
 */
@RequiredArgsConstructor
public class RoomNameValidator implements
        ConstraintValidator<RoomNameAlreadyExists, String> {

  private final RoomService roomService;

  /**
   * Validates whether the provided room name does not already exist in the system.
   *
   * @param name    The name to be validated.
   * @param context  The context in which the validation is performed.
   * @return True if the name does not exist, false if it already exists.
   */
  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return !roomService.checkOfNameAlreadyExists(name);
  }
}
