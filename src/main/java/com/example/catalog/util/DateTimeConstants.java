package com.example.catalog.util;

import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing date and time format constants for the application.
 */
@UtilityClass
public class DateTimeConstants {

  public static final DateTimeFormatter DATE_FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static final DateTimeFormatter DATE_TIME_FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
