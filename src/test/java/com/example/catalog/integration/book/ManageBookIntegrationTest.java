package com.example.catalog.integration.book;

import static com.example.catalog.util.ErrorMessagesConstants.BookTitleCanNotBeBlank;
import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.book.BookHelper;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.mapper.BookMapper;
import com.example.catalog.book.repository.BookRepository;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ManageBookIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private BookMapper bookMapper;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private PublisherRepository publisherRepository;

  @Autowired
  private ShelfRepository shelfRepository;

  @Autowired
  private StatusTypeRepository statusTypeRepository;

  private final ObjectMapper mapper =
          new ObjectMapper()
                  .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                  .registerModule(new JavaTimeModule());

  private Author author;
  private Publisher publisher;
  private Shelf shelf;
  private StatusType statusType;

  @BeforeEach
  void setup() {
    author = authorRepository.save(AuthorHelper.createAuthor());
    publisher = publisherRepository.save(PublisherHelper.createPublisher());
    shelf = shelfRepository.save(ShelfHelper.createShelf());
    statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());
  }

  //TODO: fix failed test, when all are run
  @Test
  @Transactional
  public void givenCorrectBookCreate_whenCreateBook_thenCorrect() throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(BookHelper.bookUrlPath)
        .content(mapper.writeValueAsString(BookHelper.createBookCreateWithAllIds(
                author.getId(), publisher.getId(), shelf.getId(), statusType.getId())))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    BookResponse bookResponse =
            mapper.readValue(response.getResponse().getContentAsString(), BookResponse.class);

    assertEquals(BookHelper.title, bookResponse.title());
    assertEquals(1, bookRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectBookCreateBlankName_whenCreateBook_thenException() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(BookHelper.bookUrlPath)
        .content(mapper.writeValueAsString(BookHelper.createEmptyBookCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(BookTitleCanNotBeBlank));
    assertEquals(0, bookRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenCorrectBookUpdate_whenUpdateBook_thenCorrect() throws Exception {
    Book book = BookHelper.createBook();
    book.setAuthor(author);
    book.setPublisher(publisher);
    book.setStatusType(statusType);
    book.setShelf(shelf);
    book = bookRepository.save(book);

    var response = mockMvc.perform(MockMvcRequestBuilders
        .put(createUrlPathWithId(BookHelper.bookUrlPath, book.getId()))
        .content(mapper.writeValueAsString(BookHelper.createBookUpdate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    BookResponse bookResponse =
            mapper.readValue(response.getResponse().getContentAsString(), BookResponse.class);

    assertEquals(book.getId(), bookResponse.id());
    assertEquals(BookHelper.titleUpdated, bookResponse.title());
    assertEquals(BookHelper.titleUpdated, bookRepository.findById(book.getId()).get().getTitle());
    assertEquals(1, bookRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectBookUpdateBlankName_whenUpdateBook_thenException() throws Exception {
    Book book = BookHelper.createBook();
    book.setAuthor(author);
    book.setPublisher(publisher);
    book.setStatusType(statusType);
    book.setShelf(shelf);
    book = bookRepository.save(book);

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(BookHelper.bookUrlPath, book.getId()))
                    .content(mapper.writeValueAsString(BookHelper.createBookUpdateBlankName()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(BookTitleCanNotBeBlank));
    assertEquals(1, bookRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectBookUpdateIdNotExists_whenUpdateBook_thenException() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(BookHelper.bookUrlPath, invalidId))
                    .content(mapper.writeValueAsString(BookHelper.createBookUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Book.class.getSimpleName(), invalidId), errorMessage);
  }

  @Test
  @Transactional
  public void givenCorrectId_whenDeleteBook_thenCorrect() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));
    Book book = bookRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(BookHelper.bookUrlPath, book.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(BookHelper.testBooksCount - 1, bookRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectId_whenDeleteBook_thenException() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(BookHelper.bookUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Book.class.getSimpleName(), invalidId), errorMessage);
    assertEquals(BookHelper.testBooksCount, bookRepository.findAll().size());
  }
}
