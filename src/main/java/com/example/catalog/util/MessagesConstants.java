package com.example.catalog.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides error messages constants.
 */
@UtilityClass
public class MessagesConstants {

  public static final String PasswordMustMatchDefaultMessage = "Passwords must match!";
  public static final String StrongPasswordDefaultMessage =
          "Must be 8 characters long and combination of uppercase letters, "
        + "lowercase letters, numbers, special characters.";
  public static final String PasswordAndConfirmPasswordMatchingMessage =
          "Password and Confirm Password must be matched!";
  public static final String PasswordCanNotBeBlankErrorMessage = "Password is required";
  public static final String ConfirmPasswordCanNotBeBlankErrorMessage =
          "Confirm Password is required";
  public static final String LoginIsRequiredMessage = "Login is required.";
  public static final String LoginSizeMinMessage = "Login must be at least 5 characters long.";
  public static final String PasswordSizeMinMessage =
          "Password must be at least 8 characters long.";
  public static final String LoginAlreadyExists = "This login already exists in the system.";
  public static final String AuthorNameAlreadyExists = "Author with this name already exists.";
  public static final String AuthorNameCanNotBeBlank = "You need to provide author name.";
  public static final String ShelfLetterNotBeBlank = "You need to provide letter for shelf.";
  public static final String ShelfNumberNotBeBlank = "You need to provide number for shelf.";
  public static final String ShelfRoomNotBeBlank = "You need to provide room for shelf.";
  public static final String BookTitleCanNotBeBlank = "You need to provide title for book.";
  public static final String BookTitleAlreadyExists = "Book with this title already exists.";
  public static final String AuthorCanNotBeBlank = "You need to provide author.";
  public static final String PublisherCanNotBeBlank = "You need to provide publisher.";
  public static final String StatusTypeCanNotBeBlank = "You need to provide status type.";
  public static final String PublisherNameAlreadyExists =
          "Publisher with this name already exists.";
  public static final String PublisherNameCanNotBeBlank = "You need to provide publisher name.";

  public static final String RoomNameAlreadyExists =
          "Room with this name already exists.";
  public static final String RoomNameCanNotBeBlank = "You need to provide room name.";

  public static final String StatusTypeNameAlreadyExists =
          "StatusType with this name already exists.";
  public static final String StatusTypeNameCanNotBeBlank = "You need to provide status type name.";
  public static final String SuccessfulLoginMessage = "You successfully logged in!";

  /**
   * Takes entity name and its id and creates error message entity not found.
   *
   * @param entityName String entity name. Entity.class.getSimpleName().
   * @param id Long entity's id.
   * @return String with entity not found message.
   */
  public static String createEntityNotExistsMessage(String entityName, Long id) {

    return String.format("%s with id: %d is not found.", entityName, id);
  }

  public static String createUserWithLoginNotExistsMessage(String entityName, String login) {

    return String.format("%s with login: %s is not found.", entityName, login);
  }
}
