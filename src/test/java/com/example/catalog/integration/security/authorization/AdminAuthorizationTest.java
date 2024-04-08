package com.example.catalog.integration.security.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.book.BookHelper;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.repository.BookRepository;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test checks if admin has access to all endpoints.
 */
@WithMockUser(roles = {"ADMIN"})
public class AdminAuthorizationTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private Environment environment;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private ShelfRepository shelfRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private PublisherRepository publisherRepository;

  @Autowired
  private StatusTypeRepository statusTypeRepository;

  @Autowired
  private BookRepository bookRepository;

  private final ObjectMapper mapper = new ObjectMapper();

  private User userAdmin;
  private User userUserStartup;
  private User user;
  private Room room;
  private Shelf shelf;
  private Author author;
  private Publisher publisher;
  private StatusType statusType;
  private Book book;

  @BeforeEach
  void setup() {
    userAdmin = userService.getUserByLogin(
          environment.getProperty("defaultCredentials.admin.login")
    );
    userUserStartup = userService.getUserByLogin(
          environment.getProperty("defaultCredentials.user.login")
    );
    UserResponse userResponse = userService.createUser(UserHelper.createUserCreate());
    user = userRepository.findByLogin(userResponse.login()).get();
    userRepository.save(user);
    room = roomRepository.save(RoomHelper.createRoom());
    shelf = shelfRepository.save(ShelfHelper.createShelf());
    author = authorRepository.save(AuthorHelper.createAuthor());
    publisher = publisherRepository.save(PublisherHelper.createPublisher());
    statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());
    book = bookRepository.save(
          BookHelper.createBookWithGivenAllSubEntities(author, publisher, shelf, statusType)
    );
  }

  @Test
  @Transactional
  public void adminAuthorizationOnUserEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(UserHelper.userUrlPath, user.getId()))
                .content(mapper.writeValueAsString(UserHelper.createUserUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(UserHelper.userUrlPath)
                .content(mapper.writeValueAsString(
                      UserHelper.createUserCreateWithGivenLogin("LoginTest123"))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(UserHelper.userUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(UserHelper.userUrlPath, userAdmin.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(UserHelper.userUrlPath, user.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  public void adminAuthorizationOnRoomEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(RoomHelper.roomUrlPath, room.getId()))
                .content(mapper.writeValueAsString(RoomHelper.createRoomUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(RoomHelper.roomUrlPath)
                .content(mapper.writeValueAsString(RoomHelper.createRoomWithName("Room Test 123")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(RoomHelper.roomUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(RoomHelper.roomUrlPath, room.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(RoomHelper.roomUrlPath, room.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  public void adminAuthorizationOnShelfEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(ShelfHelper.shelfUrlPath, shelf.getId()))
                .content(mapper.writeValueAsString(ShelfHelper.createShelfUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(ShelfHelper.shelfUrlPath)
                .content(mapper.writeValueAsString(
                      ShelfHelper.createShelfCreateWithGivenRoomId(room.getId()))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(ShelfHelper.shelfUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(ShelfHelper.shelfUrlPath, shelf.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(ShelfHelper.shelfUrlPath, shelf.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  public void adminAuthorizationOnAuthorEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(AuthorHelper.authorUrlPath, author.getId()))
                .content(mapper.writeValueAsString(AuthorHelper.createAuthorUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(AuthorHelper.authorUrlPath)
                .content(mapper.writeValueAsString(AuthorHelper.createAuthor()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(AuthorHelper.authorUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(AuthorHelper.authorUrlPath, author.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(AuthorHelper.authorUrlPath, author.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  public void adminAuthorizationOnPublisherEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(
          PublisherHelper.publisherUrlPath, publisher.getId())
          )
                .content(mapper.writeValueAsString(PublisherHelper.createPublisherUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(PublisherHelper.publisherUrlPath)
                .content(mapper.writeValueAsString(PublisherHelper.createPublisher()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(PublisherHelper.publisherUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(
          PublisherHelper.publisherUrlPath, publisher.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(
          PublisherHelper.publisherUrlPath, publisher.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  public void adminAuthorizationOnStatusTypeEntity() throws Exception {

    mockMvc.perform(put(createPathWithBaseUrlAndId(
          StatusTypeHelper.statusTypeUrlPath, statusType.getId()))
                .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(post(StatusTypeHelper.statusTypeUrlPath)
                .content(mapper.writeValueAsString(StatusTypeHelper.createStatusType()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(get(StatusTypeHelper.statusTypeUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(
          StatusTypeHelper.statusTypeUrlPath, statusType.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(
          StatusTypeHelper.statusTypeUrlPath, statusType.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }


  // TODO: same problem with status type when all tests go
  @Test
  @Transactional
  public void adminAuthorizationOnBookEntity() throws Exception {

    mockMvc.perform(post(BookHelper.bookUrlPath)
                .content(mapper.writeValueAsString(
                      BookHelper.createBookCreateWithAllIds(
                            author.getId(), publisher.getId(), shelf.getId(), statusType.getId()))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated());

    mockMvc.perform(put(createPathWithBaseUrlAndId(BookHelper.bookUrlPath, book.getId()))
                .content(mapper.writeValueAsString(BookHelper.createBookUpdate()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(BookHelper.bookUrlPath)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(get(createPathWithBaseUrlAndId(BookHelper.bookUrlPath, book.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(createPathWithBaseUrlAndId(BookHelper.bookUrlPath, book.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
  }
}
