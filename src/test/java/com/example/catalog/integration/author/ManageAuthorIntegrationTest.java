package com.example.catalog.integration.author;

import static com.example.catalog.util.ErrorMessagesConstants.AuthorNameAlreadyExists;
import static com.example.catalog.util.ErrorMessagesConstants.AuthorNameCanNotBeBlank;
import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.mapper.AuthorMapper;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ManageAuthorIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private AuthorMapper authorMapper;

  private final ObjectMapper mapper =
      new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  public void givenCorrectAuthorCreate_whenCreateAuthor_thenCorrect() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(AuthorHelper.authorUrlPath)
            .content(mapper.writeValueAsString(AuthorHelper.createAuthorCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    AuthorResponse authorResponse =
            mapper.readValue(response.getResponse().getContentAsString(), AuthorResponse.class);

    assertEquals(AuthorHelper.name, authorResponse.name());
    assertEquals(1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectAuthorCreateExistingName_whenCreateAuthor_thenException()
          throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .post(AuthorHelper.authorUrlPath)
        .content(mapper.writeValueAsString(AuthorHelper.createAuthorCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(AuthorHelper.authorUrlPath)
        .content(mapper.writeValueAsString(AuthorHelper.createAuthorCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthorNameAlreadyExists));
    assertEquals(1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectAuthorCreateBlankName_whenCreateAuthor_thenException()
          throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(AuthorHelper.authorUrlPath)
        .content(mapper.writeValueAsString(AuthorHelper.createEmptyAuthorCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthorNameCanNotBeBlank));
    assertEquals(0, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenCorrectAuthorUpdate_whenUpdateAuthor_thenCorrect() throws Exception {
    Author author = authorRepository.save(AuthorHelper.createAuthor());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(AuthorHelper.authorUrlPath, author.getId()))
                    .content(mapper.writeValueAsString(AuthorHelper.createAuthorUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    AuthorResponse authorResponse =
            mapper.readValue(response.getResponse().getContentAsString(), AuthorResponse.class);

    assertEquals(author.getId(), authorResponse.id());
    assertEquals(AuthorHelper.nameUpdated, authorResponse.name());
    assertEquals(
        AuthorHelper.nameUpdated,
        authorRepository.findById(author.getId()).get().getName()
    );
    assertEquals(1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectAuthorUpdateBlankName_whenUpdateAuthor_thenException()
          throws Exception {
    Author author = authorRepository.save(AuthorHelper.createAuthor());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(AuthorHelper.authorUrlPath, author.getId()))
                    .content(mapper.writeValueAsString(AuthorHelper.createAuthorUpdateBlankName()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthorNameCanNotBeBlank));
    assertEquals(1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectAuthorUpdateNameAlreadyExists_whenUpdateAuthor_thenException()
          throws Exception {
    Author author = authorRepository.save(AuthorHelper.createAuthor());

    var response = mockMvc.perform(MockMvcRequestBuilders
        .put(createUrlPathWithId(AuthorHelper.authorUrlPath, author.getId()))
        .content(mapper.writeValueAsString(AuthorHelper.createAuthorUpdateWithExistingName()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthorNameAlreadyExists));
    assertEquals(1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectAuthorUpdateIdNotExists_whenUpdateAuthor_thenException()
          throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(AuthorHelper.authorUrlPath, invalidId))
                    .content(mapper.writeValueAsString(AuthorHelper.createAuthorUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
        createEntityNotExistsMessage(Author.class.getSimpleName(), invalidId),
        errorMessage
    );
  }

  @Test
  @Transactional
  public void givenCorrectId_whenDeleteAuthor_thenCorrect() throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));
    Author author = authorRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(AuthorHelper.authorUrlPath, author.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(AuthorHelper.testAuthorsCount - 1, authorRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectId_whenDeleteAuthor_thenException() throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(AuthorHelper.authorUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
        createEntityNotExistsMessage(Author.class.getSimpleName(), invalidId),
        errorMessage
    );
    assertEquals(AuthorHelper.testAuthorsCount, authorRepository.findAll().size());
  }
}
