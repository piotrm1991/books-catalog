package com.example.catalog.room.service;

import com.example.catalog.room.entity.Room;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing room requests.
 */
public interface RoomService {

  /**
   * Create a new room request.
   *
   * @param roomCreate The request containing room details.
   * @return The response containing the created room.
   */
  RoomResponse createRoom(RoomCreate roomCreate);

  /**
   * Update an existing room request by ID.
   *
   * @param id           The ID of the room request to update.
   * @param roomUpdate The request containing updated room details.
   * @return The response containing the updated room.
   */
  RoomResponse updateRoom(Long id, RoomUpdate roomUpdate);

  /**
   * Retrieve room response by its ID.
   *
   * @param id The ID of the room response to retrieve.
   * @return The room response if found.
   */
  RoomResponse getRoomResponseById(Long id);

  /**
   * Retrieve a paginated list of room responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing room responses.
   */
  Page<RoomResponse> getAllRoomsPage(Pageable pageable);

  /**
   * Delete Room entity by its ID.
   *
   * @param id The ID of the Room entity to delete.
   */
  void deleteRoomById(Long id);

  /**
   * Retrieve Room entity by its ID.
   *
   * @param id The ID of the Room entity to retrieve.
   * @return The Room entity if found.
   */
  Room getRoomById(Long id);

  /**
   * Method checks if room name already exists in the catalog.
   *
   * @param name String room name to be checked.
   * @return boolean true if exits, false if not.
   */
  boolean checkOfNameAlreadyExists(String name);
}
