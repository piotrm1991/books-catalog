package com.example.catalog.publisher.controller;

import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.publisher.service.PublisherService;
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
 * Controller class for handling publisher-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/publishers")
@RestController
public class PublisherController {

  private final PublisherService publisherService;

  /**
   * Handles HTTP POST requests for creating a new publisher.
   *
   * @param publisherCreate The PublisherCreate request body containing publisher creation details.
   * @return A PublisherResponse representing the created publisher.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PublisherResponse createPublisher(@Valid @RequestBody PublisherCreate publisherCreate) {
    log.info("POST-request: creating publisher with name: {}", publisherCreate.name());

    return  publisherService.createPublisher(publisherCreate);
  }

  /**
   * Handles HTTP PUT requests for updating publisher by ID.
   *
   * @param id         The ID of the publisher to be updated.
   * @param publisherUpdate The PublisherUpdate request body containing publisher update details.
   * @return A PublisherResponse representing the updated publisher.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  public PublisherResponse updatePublisherById(
      @PathVariable Long id,
      @Valid @RequestBody PublisherUpdate publisherUpdate) {
    log.info("PUT-request: updating publisher with id: {}", id);

    return publisherService.updatePublisher(id, publisherUpdate);
  }

  /**
   * Handles HTTP GET requests for getting user by id.
   *
   * @param id      The ID of requested user.
   * @return A UserResponse representing the requested user.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PublisherResponse getPublisherById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return publisherService.getPublisherResponseById(id);
  }

  /**
   * Retrieve a paginated list of publisher responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing publisher responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<PublisherResponse> getAllPublishers(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all publishers.");

    return publisherService.getAllPublishersPage(pageable);
  }

  /**
   * Delete publisher request by ID.
   *
   * @param id The ID of the publisher to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePublisher(@PathVariable Long id) {
    log.info("DELETE-request: deleting publisher with id: {}", id);
    publisherService.deletePublisherById(id);
  }
}
