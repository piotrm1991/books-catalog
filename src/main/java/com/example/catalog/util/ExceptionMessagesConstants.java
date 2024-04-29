package com.example.catalog.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class with exception error messages constants.
 */
@UtilityClass
public class ExceptionMessagesConstants {

  public static final String ENTITY_WITH_THIS_NAME_ALREADY_EXISTS =
          "Entity with this name already exists.";

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

  /**
   * Take string login and creates error message user already exists.
   *
   * @param login String login to check.
   * @return String user already exists message.
   */
  public static String createUserWithLoginNotExistsMessage(String login) {

    return String.format("User with login: %s is not found.", login);
  }
}
