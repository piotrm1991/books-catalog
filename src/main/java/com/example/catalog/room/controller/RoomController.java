package com.example.catalog.room.controller;

import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;
import com.example.catalog.room.service.RoomService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling room-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rooms")
@RestController
public class RoomController {

  private final RoomService roomService;

  /**
   * Handles HTTP POST requests for creating a new room.
   *
   * @param roomCreate The RoomCreate request body containing room creation details.
   * @return A RoomResponse representing the created room.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public RoomResponse createRoom(@Valid @RequestBody RoomCreate roomCreate) {
    log.info("POST-request: creating room with name: {}", roomCreate.name());

    return  roomService.createRoom(roomCreate);
  }

  /**
   * Handles HTTP PUT requests for updating room by ID.
   *
   * @param id         The ID of the room to be updated.
   * @param roomUpdate The RoomUpdate request body containing room update details.
   * @return A RoomResponse representing the updated room.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public RoomResponse updateRoomById(
      @PathVariable Long id,
      @Valid @RequestBody RoomUpdate roomUpdate) {
    log.info("PUT-request: updating room with id: {}", id);

    return roomService.updateRoom(id, roomUpdate);
  }

  /**
   * Handles HTTP GET requests for getting user by id.
   *
   * @param id      The ID of requested user.
   * @return A UserResponse representing the requested user.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public RoomResponse getRoomById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return roomService.getRoomResponseById(id);
  }

  /**
   * Retrieve a paginated list of room responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing room responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public Page<RoomResponse> getAllRooms(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all rooms.");

    return roomService.getAllRoomsPage(pageable);
  }

  /**
   * Delete room request by ID.
   *
   * @param id The ID of the room to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteRoom(@PathVariable Long id) {
    log.info("DELETE-request: deleting room with id: {}", id);
    roomService.deleteRoomById(id);
  }
}
