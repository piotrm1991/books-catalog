package com.example.catalog.book.mapper;

import com.example.catalog.book.entity.Book;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between book-related entities and DTOs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BookMapper {

  private final ObjectMapper mapper;

  /**
   * Maps BookCreate record to Book entity.
   *
   * @param bookCreate The BookCreate record to be mapped.
   * @return Book entity.
   */
  public Book mapBookCreateToEntity(BookCreate bookCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(bookCreate), Book.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping book create to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps Book entity to a BookResponse DTO.
   *
   * @param book The Book entity to be mapped.
   * @return A BookResponse DTO.
   */
  public BookResponse mapEntityToResponse(Book book) {
    log.info("Mapping book to book response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(book), BookResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping book entity to response " + e.getMessage(), e);
    }
  }

  /**
   * Maps BookUpdate dto to a Book entity.
   *
   * @param book The Book entity to be updated.
   * @param bookUpdate BookUpdate record dto containing update details.
   * @return Updated Book entity.
   */
  public Book mapBookUpdateToEntity(Book book, BookUpdate bookUpdate) {
    log.info("Mapping book update to book with id: {}", book.getId());
    if (bookUpdate.title() != null) {
      book.setTitle(bookUpdate.title());
    }

    return book;
  }
}
