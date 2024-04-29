package com.example.catalog.shelf.service;

import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing shelf requests.
 */
public interface ShelfService {

  /**
   * Create a new shelf request.
   *
   * @param shelfCreate The request containing shelf details.
   * @return The response containing the created shelf.
   */
  ShelfResponse createShelf(ShelfCreate shelfCreate);

  /**
   * Update an existing shelf request by ID.
   *
   * @param id           The ID of the shelf request to update.
   * @param shelfUpdate The request containing updated shelf details.
   * @return The response containing the updated shelf.
   */
  ShelfResponse updateShelf(Long id, ShelfUpdate shelfUpdate);

  /**
   * Retrieve shelf response by its ID.
   *
   * @param id The ID of the shelf response to retrieve.
   * @return The shelf response if found.
   */
  ShelfResponse getShelfResponseById(Long id);

  /**
   * Retrieve a paginated list of shelf responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing shelf responses.
   */
  Page<ShelfResponse> getAllShelvesPage(Pageable pageable);

  /**
   * Delete Shelf entity by its ID.
   *
   * @param id The ID of the Shelf entity to delete.
   */
  void deleteShelfById(Long id);

  /**
   * Retrieve Shelf entity by its ID.
   *
   * @param id The ID of the Shelf entity to retrieve.
   * @return The Shelf entity if found.
   */
  Shelf getShelfById(Long id);

  /**
   * Retrieve a list of shelf responses.
   *
   * @return A {@link List} containing ShelfResponse records.
   */
  List<ShelfResponse> getAllShelvesList();
}
