package com.example.catalog.book.controller;

import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.book.service.BookService;
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
 * Controller class for handling book-related HTTP requests.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {
  
  private final BookService bookService;

  /**
   * Handles HTTP POST requests for creating a new book.
   *
   * @param bookCreate The BookCreate request body containing book creation details.
   * @return A BookResponse representing the created book.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public BookResponse createBook(@Valid @RequestBody BookCreate bookCreate) {
    log.info("POST-request: creating book with title: {}", bookCreate.title());

    return  bookService.createBook(bookCreate);
  }

  /**
   * Handles HTTP PUT requests for updating book by ID.
   *
   * @param id         The ID of the book to be updated.
   * @param bookUpdate The BookUpdate request body containing book update details.
   * @return A BookResponse representing the updated book.
   */
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public BookResponse updateBookById(
          @PathVariable Long id,
          @Valid @RequestBody BookUpdate bookUpdate) {
    log.info("PUT-request: updating book with id: {}", id);

    return bookService.updateBook(id, bookUpdate);
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
  public BookResponse getBookById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return bookService.getBookResponseById(id);
  }

  /**
   * Retrieve a paginated list of book responses.
   *
   * @param pageable        The pagination information.
   * @return A {@link Page} containing book responses.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public Page<BookResponse> getAllBooks(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all books.");

    return bookService.getAllBooksPage(pageable);
  }

  /**
   * Delete book request by ID.
   *
   * @param id The ID of the book to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public void deleteBook(@PathVariable Long id) {
    log.info("DELETE-request: deleting book with id: {}", id);
    bookService.deleteBookById(id);
  }
}
