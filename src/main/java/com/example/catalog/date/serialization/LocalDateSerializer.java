package com.example.catalog.date.serialization;

import com.example.catalog.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Custom serializer for LocalDate objects to format them as strings in "yyyy-MM-dd" format.
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

  /**
   * Serializes a LocalDate object to a string in "yyyy-MM-dd" format and writes it
   * to the JSON generator.
   *
   * @param localDate the LocalDate object to be serialized
   * @param jsonGenerator the JSON generator used to write the serialized string
   * @param serializerProvider the serializer provider
   * @throws IOException if an I/O error occurs during serialization
   */
  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDate.format(DateTimeConstants.DATE_FORMATTER));
  }
}
