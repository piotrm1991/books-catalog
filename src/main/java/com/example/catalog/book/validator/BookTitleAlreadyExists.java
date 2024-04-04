//package com.example.catalog.book.validator;
//
//import static com.example.catalog.util.ErrorMessagesConstants.BookTitleAlreadyExists;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
///**
// * Custom validation annotation to ensure that
// * the provided title of the book does not exist in the system.
// */
//@Constraint(validatedBy = BookTitleValidator.class)
//@Target({ ElementType.METHOD, ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//public  @interface BookTitleAlreadyExists {
//
//  /**
//   * The validation error message to be displayed when the title already exists.
//   *
//   * @return The validation error message.
//   */
//  String message() default BookTitleAlreadyExists;
//
//  /**
//   * Specifies the validation groups to which this constraint belongs.
//   *
//   * @return An array of validation groups.
//   */
//  Class<?>[] groups() default {};
//
//  /**
//   * Specifies additional data to be provided when a validation error occurs.
//   *
//   * @return An array of payload classes.
//   */
//  Class<? extends Payload>[] payload() default {};
//}
