package com.example.catalog.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides error messages constants.
 */
@UtilityClass
public class ErrorMessagesConstants {

  public static final String AuthorNameAlreadyExists = "Author with this name already exists.";
  public static final String AuthorNameCanNotBeBlank = "You need to provide author name.";
  public static final String ShelfLetterNotBeBlank = "You need to provide letter for shelf.";
  public static final String ShelfNumberNotBeBlank = "You need to provide number for shelf.";
  public static final String ShelfRoomNotBeBlank = "You need to provide room for shelf.";
  public static final String PublisherNameAlreadyExists =
          "Publisher with this name already exists.";
  public static final String PublisherNameCanNotBeBlank = "You need to provide publisher name.";

  public static final String RoomNameAlreadyExists =
          "Room with this name already exists.";
  public static final String RoomNameCanNotBeBlank = "You need to provide room name.";

  public static final String StatusTypeNameAlreadyExists =
          "StatusType with this name already exists.";
  public static final String StatusTypeNameCanNotBeBlank = "You need to provide status type name.";

  public static String createAuthorNotExistMessage(Long id) {

    return String.format("Author with id: %d is not found.", id);
  }

  public static String createPublisherNotExistMessage(Long id) {

    return String.format("Publisher with id: %d is not found.", id);
  }

  public static String createRoomNotExistMessage(Long id) {

    return String.format("Room with id: %d is not found.", id);
  }

  public static String createStatusTypeNotExistMessage(Long id) {

    return String.format("StatusType with id: %d is not found.", id);
  }

  public static String createShelfNotExistMessage(Long id) {

    return String.format("Shelf with id: %d is not found.", id);
  }
}
