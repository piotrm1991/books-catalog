package com.example.catalog.integration.author;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.mapper.AuthorMapper;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.RestPageImpl;
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

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ViewAuthorsIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private AuthorMapper authorMapper;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  public void givenCorrectId_whenGetAuthorById_theReturnAuthorResponseCorrect() throws Exception {
    Author expectedAuthor = AuthorHelper.createAuthor();
    expectedAuthor = authorRepository.save(expectedAuthor);

    var response = mockMvc.perform(get(createUrlPathWithId(AuthorHelper.authorUrlPath, expectedAuthor.getId()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    AuthorResponse authorResponse = mapper.readValue(response.getResponse().getContentAsString(), AuthorResponse.class);

    assertEquals(expectedAuthor.getId(), authorResponse.id());
    assertEquals(expectedAuthor.getName(), authorResponse.name());

    assertThat(authorRepository.getReferenceById(expectedAuthor.getId())).isEqualTo(expectedAuthor);
  }

  @Test
  @Transactional
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(get(createUrlPathWithId(AuthorHelper.authorUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains("Author with id: " + invalidId + " is not found."));
  }

  @Test
  @Transactional
  public void testGetAllAuthors_defaultPageRequest() throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));

    var response = mockMvc.perform(get(AuthorHelper.authorUrlPath)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<AuthorResponse> authorsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<AuthorResponse>>() {});

    assertFalse(authorsResponse.isEmpty());
    assertEquals( AuthorHelper.testAuthorsCount, authorsResponse.getTotalElements());
    assertEquals(4, authorsResponse.getTotalPages());
    assertEquals(5, authorsResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(authorsResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(authorMapper.mapEntityToResponse(authorList.get(i)));
    }
  }

  @Test
  @Transactional
  public void testGetAllAuthors_empty() throws Exception {
    var response = mockMvc.perform(get(AuthorHelper.authorUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<AuthorResponse> authorsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<AuthorResponse>>() {});

    assertTrue(authorsResponse.isEmpty());
    assertEquals( 0, authorsResponse.getTotalElements());
    assertEquals(0, authorsResponse.getTotalPages());
    assertEquals(0, authorsResponse.getContent().size());
  }

  @Test
  @Transactional
  public void testGetAllAuthors_customPageRequest() throws Exception {
    List<Author> authorList = AuthorHelper.prepareAuthorList();
    authorList.forEach(a -> authorRepository.save(a));
    Page<Author> expectedAuthorList = authorRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

    var response = mockMvc.perform(get(createUrlPathGetPageable(AuthorHelper.authorUrlPath, 1, 3, "id", false))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<AuthorResponse> authorsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<AuthorResponse>>() {});

    assertFalse(authorsResponse.isEmpty());
    assertEquals(expectedAuthorList.getTotalElements(), authorsResponse.getTotalElements());
    assertEquals(expectedAuthorList.getTotalPages(), authorsResponse.getTotalPages());
    assertEquals(expectedAuthorList.getContent().size(), authorsResponse.getContent().size());

    for (int i = 0; i < expectedAuthorList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(authorsResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(authorMapper.mapEntityToResponse(expectedAuthorList.getContent().get(i)));
    }
  }
}
