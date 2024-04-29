package com.example.catalog.publisher.service;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing publisher requests.
 */
public interface PublisherService {

  /**
   * Create a new publisher request.
   *
   * @param publisherCreate The request containing publisher details.
   * @return The response containing the created publisher.
   */
  PublisherResponse createPublisher(PublisherCreate publisherCreate);

  /**
   * Update an existing publisher request by ID.
   *
   * @param id           The ID of the publisher request to update.
   * @param publisherUpdate The request containing updated publisher details.
   * @return The response containing the updated publisher.
   */
  PublisherResponse updatePublisher(Long id, PublisherUpdate publisherUpdate);

  /**
   * Retrieve publisher response by its ID.
   *
   * @param id The ID of the publisher response to retrieve.
   * @return The publisher response if found.
   */
  PublisherResponse getPublisherResponseById(Long id);

  /**
   * Retrieve a paginated list of publisher responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing publisher responses.
   */
  Page<PublisherResponse> getAllPublishersPage(Pageable pageable);

  /**
   * Delete Publisher entity by its ID.
   *
   * @param id The ID of the Publisher entity to delete.
   */
  void deletePublisherById(Long id);

  /**
   * Retrieve Publisher entity by its ID.
   *
   * @param id The ID of the Publisher entity to retrieve.
   * @return The Publisher entity if found.
   */
  Publisher getPublisherById(Long id);

  /**
   * Method checks if publisher name already exists in the catalog.
   *
   * @param name String publisher name to be checked.
   * @return boolean true if exits, false if not.
   */
  boolean checkOfNameAlreadyExists(String name);

  /**
   * Retrieve a list of publisher responses.
   *
   * @return A {@link List} containing publisher responses.
   */
  List<PublisherResponse> getAllPublishersList();
}
