package com.example.catalog.book.service;

import com.example.catalog.book.entity.Book;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing book requests.
 */
public interface BookService {

  /**
   * Create a new book request.
   *
   * @param bookCreate The request containing book details.
   * @return The response containing the created book.
   */
  BookResponse createBook(BookCreate bookCreate);

  /**
   * Update an existing book request by ID.
   *
   * @param id           The ID of the book request to update.
   * @param bookUpdate The request containing updated book details.
   * @return The response containing the updated book.
   */
  BookResponse updateBook(Long id, BookUpdate bookUpdate);

  /**
   * Retrieve book response by its ID.
   *
   * @param id The ID of the book response to retrieve.
   * @return The book response if found.
   */
  BookResponse getBookResponseById(Long id);

  /**
   * Retrieve a paginated list of book responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing book responses.
   */
  Page<BookResponse> getAllBooksPage(Pageable pageable);

  /**
   * Delete Book entity by its ID.
   *
   * @param id The ID of the Book entity to delete.
   */
  void deleteBookById(Long id);

  /**
   * Retrieve Book entity by its ID.
   *
   * @param id The ID of the Book entity to retrieve.
   * @return The Book entity if found.
   */
  Book getBookById(Long id);
}
