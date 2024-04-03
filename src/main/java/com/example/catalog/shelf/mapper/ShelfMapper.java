package com.example.catalog.shelf.mapper;

import com.example.catalog.room.entity.Room;
import com.example.catalog.room.service.RoomService;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between shelf-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShelfMapper {

  private final ObjectMapper mapper;
  private final RoomService roomService;

  /**
   * Maps ShelfCreate record to Shelf entity.
   *
   * @param shelfCreate The ShelfCreate record to be mapped.
   * @return Shelf entity.
   */
  public Shelf mapShelfCreateToEntity(ShelfCreate shelfCreate) {
    log.info("Mapping shelf creation to shelf.");
    try {

      return mapper.readValue(mapper.writeValueAsString(shelfCreate), Shelf.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping shelf create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps Shelf entity to a ShelfResponse DTO.
   *
   * @param shelf The Shelf entity to be mapped.
   * @return ShelfResponse DTO.
   */
  public ShelfResponse mapEntityToResponse(Shelf shelf) {
    log.info("Mapping shelf to shelf response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(shelf), ShelfResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Error while mapping shelf entity to response " + e.getMessage(), e);
    }
  }

  /**
   * Maps ShelfUpdate dto to a Shelf entity.
   *
   * @param shelf The Shelf entity to be updated.
   * @param shelfUpdate ShelfUpdate record dto containing update details.
   * @return Updated Shelf entity.
   */
  public Shelf mapShelfUpdateToEntity(Shelf shelf, ShelfUpdate shelfUpdate) {
    log.info("Mapping shelf update to shelf with id: {}", shelf.getId());
    if (shelfUpdate.letter() != null) {
      shelf.setLetter(shelfUpdate.letter());
    }
    if (shelfUpdate.number() != null) {
      shelf.setNumber(shelfUpdate.number());
    }
    if (shelfUpdate.idRoom() != null) {
      Room room = roomService.getRoomById(shelfUpdate.idRoom());
      shelf.setRoom(room);
    }

    return shelf;
  }
}
