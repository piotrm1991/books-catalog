package com.example.catalog.statustype.service;

import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing statusType requests.
 */
public interface StatusTypeService {

  /**
   * Create a new statusType request.
   *
   * @param statusTypeCreate The request containing statusType details.
   * @return The response containing the created statusType.
   */
  StatusTypeResponse createStatusType(StatusTypeCreate statusTypeCreate);

  /**
   * Update an existing statusType request by ID.
   *
   * @param id           The ID of the statusType request to update.
   * @param statusTypeUpdate The request containing updated statusType details.
   * @return The response containing the updated statusType.
   */
  StatusTypeResponse updateStatusType(Long id, StatusTypeUpdate statusTypeUpdate);

  /**
   * Retrieve statusType response by its ID.
   *
   * @param id The ID of the statusType response to retrieve.
   * @return The statusType response if found.
   */
  StatusTypeResponse getStatusTypeResponseById(Long id);

  /**
   * Retrieve a paginated list of statusType responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing statusType responses.
   */
  Page<StatusTypeResponse> getAllStatusTypesPage(Pageable pageable);

  /**
   * Delete StatusType entity by its ID.
   *
   * @param id The ID of the StatusType entity to delete.
   */
  void deleteStatusTypeById(Long id);

  /**
   * Retrieve StatusType entity by its ID.
   *
   * @param id The ID of the StatusType entity to retrieve.
   * @return The StatusType entity if found.
   */
  StatusType getStatusTypeById(Long id);

  /**
   * Method checks if statusType name already exists in the catalog.
   *
   * @param name String statusType name to be checked.
   * @return boolean true if exits, false if not.
   */
  boolean checkOfNameAlreadyExists(String name);
}
