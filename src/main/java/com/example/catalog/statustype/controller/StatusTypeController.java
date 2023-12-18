package com.example.catalog.statustype.controller;

import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.example.catalog.statustype.service.StatusTypeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
 * Controller class for handling statusType-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/statusTypes")
@RestController
public class StatusTypeController {

  private final StatusTypeService statusTypeService;

  /**
   * Handles HTTP POST requests for creating a new statusType.
   *
   * @param statusTypeCreate The StatusTypeCreate
   *                         request body containing statusType creation details.
   * @return A StatusTypeResponse representing the created statusType.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public StatusTypeResponse createStatusType(
      @Valid @RequestBody StatusTypeCreate statusTypeCreate) {
    log.info("POST-request: creating statusType with name: {}", statusTypeCreate.name());

    return  statusTypeService.createStatusType(statusTypeCreate);
  }

  /**
   * Handles HTTP PUT requests for updating statusType by ID.
   *
   * @param id         The ID of the statusType to be updated.
   * @param statusTypeUpdate The StatusTypeUpdate request body containing statusType update details.
   * @return A StatusTypeResponse representing the updated statusType.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  public StatusTypeResponse updateStatusTypeById(
      @PathVariable Long id,
      @Valid @RequestBody StatusTypeUpdate statusTypeUpdate) {
    log.info("PUT-request: updating statusType with id: {}", id);

    return statusTypeService.updateStatusType(id, statusTypeUpdate);
  }

  /**
   * Handles HTTP GET requests for getting user by id.
   *
   * @param id      The ID of requested user.
   * @return A UserResponse representing the requested user.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public StatusTypeResponse getStatusTypeById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return statusTypeService.getStatusTypeResponseById(id);
  }

  /**
   * Retrieve a paginated list of statusType responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing statusType responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<StatusTypeResponse> getAllStatusTypes(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all statusTypes.");

    return statusTypeService.getAllStatusTypesPage(pageable);
  }

  /**
   * Delete statusType request by ID.
   *
   * @param id The ID of the statusType to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteStatusType(@PathVariable Long id) {
    log.info("DELETE-request: deleting statusType with id: {}", id);
    statusTypeService.deleteStatusTypeById(id);
  }
}
