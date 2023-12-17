package com.example.catalog.author.service.impl;

import com.example.catalog.author.entity.Author;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing author requests.
 */
public interface AuthorService {

  /**
   * Create a new author request.
   *
   * @param authorCreate The request containing author details.
   * @return The response containing the created author.
   */
  AuthorResponse createAuthor(AuthorCreate authorCreate);

  /**
   * Update an existing author request by ID.
   *
   * @param id           The ID of the author request to update.
   * @param authorUpdate The request containing updated author details.
   * @return The response containing the updated author.
   */
  AuthorResponse updateAuthor(Long id, AuthorUpdate authorUpdate);

  /**
   * Retrieve author response by its ID.
   *
   * @param id The ID of the author response to retrieve.
   * @return The author response if found.
   */
  AuthorResponse getAuthorResponseById(Long id);

  /**
   * Retrieve a paginated list of author responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing author responses.
   */
  Page<AuthorResponse> getAllAuthorsPage(Pageable pageable);

  /**
   * Delete Author entity by its ID.
   *
   * @param id The ID of the Author entity to delete.
   */
  void deleteAuthorById(Long id);

  /**
   * Retrieve Author entity by its ID.
   *
   * @param id The ID of the Author entity to retrieve.
   * @return The Author entity if found.
   */
  Author getAuthorById(Long id);

  /**
   * Method checks if author name already exists in the catalog.
   *
   * @param name String author name to be checked.
   * @return boolean true if exits, false if not.
   */
  boolean checkOfNameAlreadyExists(String name);
}
