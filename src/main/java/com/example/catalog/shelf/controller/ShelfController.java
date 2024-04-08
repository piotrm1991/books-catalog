package com.example.catalog.shelf.controller;

import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import com.example.catalog.shelf.service.ShelfService;
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
 * Controller class for handling shelf-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shelves")
@RestController
public class ShelfController {

  private final ShelfService shelfService;

  /**
   * Handles HTTP POST requests for creating a new shelf.
   *
   * @param shelfCreate The ShelfCreate request body containing shelf creation details.
   * @return A ShelfResponse representing the created shelf.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ShelfResponse createShelf(@Valid @RequestBody ShelfCreate shelfCreate) {
    log.info("POST-request: creating shelf with letter: {} and number: {}, in room id: {}",
        shelfCreate.letter(), shelfCreate.number(), shelfCreate.idRoom());

    return  shelfService.createShelf(shelfCreate);
  }

  /**
   * Handles HTTP PUT requests for updating shelf by ID.
   *
   * @param id         The ID of the shelf to be updated.
   * @param shelfUpdate The ShelfUpdate request body containing shelf update details.
   * @return A ShelfResponse representing the updated shelf.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ShelfResponse updateShelfById(
      @PathVariable Long id,
      @Valid @RequestBody ShelfUpdate shelfUpdate) {
    log.info("PUT-request: updating shelf with id: {}", id);

    return shelfService.updateShelf(id, shelfUpdate);
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
  public ShelfResponse getShelfById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return shelfService.getShelfResponseById(id);
  }

  /**
   * Retrieve a paginated list of shelf responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing shelf responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public Page<ShelfResponse> getAllShelves(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all shelves.");

    return shelfService.getAllShelvesPage(pageable);
  }

  /**
   * Delete shelf request by ID.
   *
   * @param id The ID of the shelf to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteShelf(@PathVariable Long id) {
    log.info("DELETE-request: deleting shelf with id: {}", id);
    shelfService.deleteShelfById(id);
  }
}
