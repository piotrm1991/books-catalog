//package com.example.catalog.book.validator;
//
//import com.example.catalog.book.service.BookService;
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import lombok.RequiredArgsConstructor;
//
///**
// * Custom validator that checks if the provided book title does not exist in the system.
// */
//@RequiredArgsConstructor
//public class BookTitleValidator implements ConstraintValidator<BookTitleAlreadyExists, String> {
//
//  private final BookService bookService;
//
//  /**
//   * Validates whether the provided book title does not already exist in the system.
//   *
//   * @param title    The name to be validated.
//   * @param context  The context in which the validation is performed.
//   * @return True if the name does not exist, false if it already exists.
//   */
//  @Override
//  public boolean isValid(String title, ConstraintValidatorContext context) {
//    return !bookService.checkIfTitleAlreadyExists(title);
//  }
//}
