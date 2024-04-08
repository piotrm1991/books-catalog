package com.example.catalog.integration.book;

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
import com.example.catalog.shared.RestPageImpl;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.catalog.util.MessagesConstants.createEntityNotExistsMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ViewBookIntegrationTest extends AbstractIntegrationTest {

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

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenGetBookById_theReturnBookResponseCorrect() throws Exception {
    Book expectedBook = BookHelper.createBook();
    Author author = authorRepository.save(AuthorHelper.createAuthor());
    Publisher publisher = publisherRepository.save(PublisherHelper.createPublisher());
    Shelf shelf = shelfRepository.save(ShelfHelper.createShelf());
    StatusType statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());
    expectedBook.setAuthor(author);
    expectedBook.setPublisher(publisher);
    expectedBook.setShelf(shelf);
    expectedBook.setStatusType(statusType);
    expectedBook = bookRepository.save(expectedBook);

    var response = mockMvc.perform(get(createUrlPathWithId(BookHelper.bookUrlPath, expectedBook.getId()))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    BookResponse bookResponse = mapper.readValue(response.getResponse().getContentAsString(), BookResponse.class);

    assertEquals(expectedBook.getId(), bookResponse.id());
    assertEquals(expectedBook.getTitle(), bookResponse.title());

    assertThat(bookRepository.getReferenceById(expectedBook.getId())).isEqualTo(expectedBook);
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(get(createUrlPathWithId(BookHelper.bookUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains(createEntityNotExistsMessage(Book.class.getSimpleName(), invalidId)));

  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllBooks_defaultPageRequest() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));

    var response = mockMvc.perform(get(BookHelper.bookUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<BookResponse> booksResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<BookResponse>>() {});

    assertFalse(booksResponse.isEmpty());
    assertEquals( BookHelper.testBooksCount, booksResponse.getTotalElements());
    assertEquals(2, booksResponse.getTotalPages());
    assertEquals(5, booksResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(booksResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(bookMapper.mapEntityToResponse(bookList.get(i)));
    }
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllBooks_empty() throws Exception {
    var response = mockMvc.perform(get(BookHelper.bookUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<BookResponse> booksResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<BookResponse>>() {});

    assertTrue(booksResponse.isEmpty());
    assertEquals( 0, booksResponse.getTotalElements());
    assertEquals(0, booksResponse.getTotalPages());
    assertEquals(0, booksResponse.getContent().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllBooks_customPageRequest() throws Exception {
    List<Book> bookList = BookHelper.prepareBookList();
    bookList.forEach(a -> bookRepository.save(a));
    Page<Book> expectedBookList = bookRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

    var response = mockMvc.perform(get(createUrlPathGetPageable(BookHelper.bookUrlPath, 1, 3, "id", false))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<BookResponse> booksResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<BookResponse>>() {});

    assertFalse(booksResponse.isEmpty());
    assertEquals(expectedBookList.getTotalElements(), booksResponse.getTotalElements());
    assertEquals(expectedBookList.getTotalPages(), booksResponse.getTotalPages());
    assertEquals(expectedBookList.getContent().size(), booksResponse.getContent().size());

    for (int i = 0; i < expectedBookList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(booksResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(bookMapper.mapEntityToResponse(expectedBookList.getContent().get(i)));
    }
  }
}
