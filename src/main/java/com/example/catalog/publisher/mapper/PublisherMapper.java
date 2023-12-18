package com.example.catalog.publisher.mapper;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.response.PublisherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between publisher-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PublisherMapper {

  private final ObjectMapper mapper;

  /**
   * Maps PublisherCreate record to Publisher entity.
   *
   * @param publisherCreate The PublisherCreate record to be mapped.
   * @return Publisher entity.
   */
  public Publisher mapPublisherCreateToEntity(PublisherCreate publisherCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(publisherCreate), Publisher.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping publisher create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps Publisher entity to a UserResponse DTO.
   *
   * @param publisher The Publisher entity to be mapped.
   * @return A PublisherResponse DTO.
   */
  public PublisherResponse mapEntityToResponse(Publisher publisher) {
    log.info("Mapping publisher to publisher response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(publisher), PublisherResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping publisher entity to response " + e.getMessage(), e);
    }
  }
}
