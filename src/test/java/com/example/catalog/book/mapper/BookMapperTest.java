package com.example.catalog.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.catalog.book.BookHelper;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.response.BookResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for BookMapper class.
 */
@ExtendWith(MockitoExtension.class)
public class BookMapperTest {

  @InjectMocks
  private BookMapper bookMapper;

  @Mock
  private ObjectMapper mapper;

  private Book book;
  private BookCreate bookCreate;
  private BookResponse bookResponse;

  @BeforeEach
  void setUp() {
    book = BookHelper.createBook();
    bookCreate = BookHelper.createBookCreate();
    bookResponse = BookHelper.createBookResponse();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(bookCreate), Book.class)).thenReturn(book);

    Book expectedBook = bookMapper.mapBookCreateToEntity(bookCreate);

    assertEquals(expectedBook, book);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(book), BookResponse.class))
        .thenReturn(bookResponse);

    BookResponse expectedLeaveResponse = bookMapper.mapEntityToResponse(book);

    assertEquals(expectedLeaveResponse, bookResponse);
  }
}
