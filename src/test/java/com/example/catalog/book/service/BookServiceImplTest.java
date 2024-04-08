package com.example.catalog.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.service.AuthorService;
import com.example.catalog.book.BookHelper;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.mapper.BookMapper;
import com.example.catalog.book.repository.BookRepository;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.book.service.impl.BookServiceImpl;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.service.PublisherService;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.service.ShelfService;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.service.StatusTypeService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * Unit tests for implementation of BookService interface.
 * BookServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
  @InjectMocks
  private BookServiceImpl bookService;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookMapper bookMapper;

  @Mock
  private AuthorService authorService;

  @Mock
  private PublisherService publisherService;

  @Mock
  private ShelfService shelfService;

  @Mock
  private StatusTypeService statusTypeService;

  private Book book;
  private BookCreate bookCreate;
  private BookUpdate bookUpdate;
  private BookResponse bookResponse;
  private Author author;
  private Publisher publisher;
  private Shelf shelf;
  private StatusType statusType;

  @BeforeEach
  void setUp() {
    book = BookHelper.createBook();
    bookCreate = BookHelper.createBookCreate();
    bookUpdate = BookHelper.createBookUpdate();
    bookResponse = BookHelper.createBookResponse();
    author = AuthorHelper.createAuthor();
    publisher = PublisherHelper.createPublisher();
    shelf = ShelfHelper.createShelf();
    statusType = StatusTypeHelper.createStatusType();
  }

  @Test
  void givenCorrectBookRequest_whenCreateBook_thenCorrect() {
    when(bookMapper.mapBookCreateToEntity(bookCreate)).thenReturn(book);
    when(authorService.getAuthorById(AuthorHelper.id)).thenReturn(author);
    when(publisherService.getPublisherById(PublisherHelper.id)).thenReturn(publisher);
    when(shelfService.getShelfById(ShelfHelper.id)).thenReturn(shelf);
    when(statusTypeService.getStatusTypeById(StatusTypeHelper.id)).thenReturn(statusType);
    when(bookMapper.mapEntityToResponse(book)).thenReturn(bookResponse);
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    BookResponse actualBookResponse = bookService.createBook(bookCreate);

    assertEquals(bookResponse, actualBookResponse);
    verify(bookRepository, times(1)).save(book);
  }

  @Test
  void givenCorrectBookRequest_whenUpdateBook_thenCorrect() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    when(bookMapper.mapBookUpdateToEntity(book, bookUpdate)).thenReturn(book);
    when(bookRepository.save(any(Book.class))).thenReturn(book);
    when(bookMapper.mapEntityToResponse(any(Book.class))).thenReturn(bookResponse);

    BookResponse actualBookResponse = bookService.updateBook(BookHelper.id, bookUpdate);

    assertEquals(bookResponse, actualBookResponse);
    verify(bookRepository, times(1)).save(book);
  }

  @Test
  void givenCorrectId_whenGetBookById_thenCorrect() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

    Book actualBook = bookService.getBookById(anyLong());

    assertEquals(book, actualBook);
  }

  @Test
  void givenIncorrectId_whenGetBookById_thenException() {
    Long invalidId = 1L;

    when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> bookService.getBookById(invalidId));
    String expectedMessage = "Book with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeleteBookById_thenCorrect() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

    bookService.deleteBookById(anyLong());

    verify(bookRepository, times(1)).delete(book);
  }

  @Test
  void givenIncorrectId_whenDeleteBookById_thenException() {
    Long invalidId = 1L;

    when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> bookService.deleteBookById(invalidId));
    String expectedMessage = "Book with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllBooksPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(bookRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = bookService.getAllBooksPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetBookResponseById_thenCorrect() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    when(bookMapper.mapEntityToResponse(book)).thenReturn(bookResponse);

    BookResponse actualBookResponse = bookService.getBookResponseById(anyLong());

    assertEquals(bookResponse, actualBookResponse);
  }

  @Test
  void givenIncorrectId_whenGetBookResponseById_thenException() {
    Long invalidId = 1L;

    when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> bookService.getBookResponseById(invalidId));
    String expectedMessage = "Book with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}
