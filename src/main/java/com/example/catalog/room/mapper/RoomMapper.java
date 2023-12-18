package com.example.catalog.room.mapper;

import com.example.catalog.room.entity.Room;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.response.RoomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between room-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoomMapper {

  private final ObjectMapper mapper;

  /**
   * Maps RoomCreate record to Room entity.
   *
   * @param roomCreate The RoomCreate record to be mapped.
   * @return Room entity.
   */
  public Room mapRoomCreateToEntity(RoomCreate roomCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(roomCreate), Room.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping room create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps Room entity to a UserResponse DTO.
   *
   * @param room The Room entity to be mapped.
   * @return A RoomResponse DTO.
   */
  public RoomResponse mapEntityToResponse(Room room) {
    log.info("Mapping room to room response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(room), RoomResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping room entity to response " + e.getMessage(), e);
    }
  }
}
