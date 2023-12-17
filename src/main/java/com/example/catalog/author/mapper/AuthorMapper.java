package com.example.catalog.author.mapper;

import com.example.catalog.author.entity.Author;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between author-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorMapper {

  private final ObjectMapper mapper;

  /**
   * Maps AuthorCreate record to Author entity.
   *
   * @param authorCreate The AuthorCreate record to be mapped.
   * @return Author entity.
   */
  public Author mapAuthorCreateToEntity(AuthorCreate authorCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(authorCreate), Author.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping author create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps Author entity to a UserResponse DTO.
   *
   * @param author The Author entity to be mapped.
   * @return A AuthorResponse DTO.
   */
  public AuthorResponse mapEntityToResponse(Author author) {
    log.info("Mapping author to author response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(author), AuthorResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping author entity to response " + e.getMessage(), e);
    }
  }
}
