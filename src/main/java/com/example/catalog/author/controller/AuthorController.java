package com.example.catalog.author.controller;

import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.author.service.AuthorService;
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
 * Controller class for handling author-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authors")
@RestController
public class AuthorController {

  private final AuthorService authorService;

  /**
   * Handles HTTP POST requests for creating a new author.
   *
   * @param authorCreate The AuthorCreate request body containing author creation details.
   * @return A AuthorResponse representing the created author.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public AuthorResponse createAuthor(@Valid @RequestBody AuthorCreate authorCreate) {
    log.info("POST-request: creating author with name: {}", authorCreate.name());

    return  authorService.createAuthor(authorCreate);
  }

  /**
   * Handles HTTP PUT requests for updating author by ID.
   *
   * @param id         The ID of the author to be updated.
   * @param authorUpdate The AuthorUpdate request body containing author update details.
   * @return A AuthorResponse representing the updated author.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  public AuthorResponse updateAuthorById(
      @PathVariable Long id,
      @Valid @RequestBody AuthorUpdate authorUpdate) {
    log.info("PUT-request: updating author with id: {}", id);

    return authorService.updateAuthor(id, authorUpdate);
  }

  /**
   * Handles HTTP GET requests for getting user by id.
   *
   * @param id      The ID of requested user.
   * @return A UserResponse representing the requested user.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public AuthorResponse getAuthorById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return authorService.getAuthorResponseById(id);
  }

  /**
   * Retrieve a paginated list of author responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing author responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<AuthorResponse> getAllAuthors(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all authors.");

    return authorService.getAllAuthorsPage(pageable);
  }

  /**
   * Delete author request by ID.
   *
   * @param id The ID of the author to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAuthor(@PathVariable Long id) {
    log.info("DELETE-request: deleting author with id: {}", id);
    authorService.deleteAuthorById(id);
  }
}
