package com.example.catalog.statustype.mapper;

import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between statusType-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StatusTypeMapper {

  private final ObjectMapper mapper;

  /**
   * Maps StatusTypeCreate record to StatusType entity.
   *
   * @param statusTypeCreate The StatusTypeCreate record to be mapped.
   * @return StatusType entity.
   */
  public StatusType mapStatusTypeCreateToEntity(StatusTypeCreate statusTypeCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(statusTypeCreate), StatusType.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping statusType create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps StatusType entity to a UserResponse DTO.
   *
   * @param statusType The StatusType entity to be mapped.
   * @return A StatusTypeResponse DTO.
   */
  public StatusTypeResponse mapEntityToResponse(StatusType statusType) {
    log.info("Mapping statusType to statusType response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(statusType), StatusTypeResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping statusType entity to response " + e.getMessage(), e);
    }
  }
}
