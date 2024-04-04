package com.example.catalog.date.deserialization;

import com.example.catalog.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import javax.validation.ValidationException;

/**
 * Custom deserializer for LocalDate objects to parse strings in "yyyy-MM-dd" format.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

  /**
   * Deserializes a JSON string representing a date in "yyyy-MM-dd" format into a LocalDate object.
   *
   * @param jsonParser the JSON parser containing the date string to be deserialized
   * @param deserializationContext the deserialization context
   * @return the deserialized LocalDate object
   * @throws IOException if an I/O error occurs during deserialization
   */
  @Override
  public LocalDate deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext)
          throws IOException {
    try {

      return LocalDate.parse(jsonParser.getText(), DateTimeConstants.DATE_FORMATTER);
    } catch (Exception e) {

      throw new ValidationException("Error while deserializing date use format: yyyy-MM-dd");
    }
  }
}