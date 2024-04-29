package com.example.catalog.integration.shelf;

import static com.example.catalog.util.ExceptionMessagesConstants.createEntityNotExistsMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.RestPageImpl;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.mapper.ShelfMapper;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.shelf.response.ShelfResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

/**
 * Integration tests for GET operation on Shelf entity.
 */
public class ViewShelvesIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private ShelfRepository shelfRepository;

  @Autowired
  private ShelfMapper shelfMapper;

  private final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenGetShelfById_theReturnShelfResponseCorrect() throws Exception {
    Shelf expectedShelf = ShelfHelper.createShelf();
    expectedShelf = shelfRepository.save(expectedShelf);

    var response = mockMvc
          .perform(get(createUrlPathWithId(ShelfHelper.shelfUrlPath, expectedShelf.getId()))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

    ShelfResponse shelfResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          ShelfResponse.class
    );

    assertEquals(expectedShelf.getId(), shelfResponse.id());
    assertEquals(expectedShelf.getLetter(), shelfResponse.letter());
    assertEquals(expectedShelf.getNumber(), shelfResponse.number());

    assertThat(shelfRepository.getReferenceById(expectedShelf.getId()))
          .isEqualTo(expectedShelf);
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc
          .perform(get(createUrlPathWithId(ShelfHelper.shelfUrlPath, invalidId))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound())
          .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains(createEntityNotExistsMessage(
          Shelf.class.getSimpleName(),
          invalidId
    )));

  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllShelvesWithParametersPageAndSize_defaultPageRequest() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));

    var response = mockMvc.perform(get(ShelfHelper.getShelfUrlPathWithPageAndSize())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andExpect(jsonPath("$['content']", hasSize(ShelfHelper.size)))
            .andExpect(jsonPath("$['totalElements']").value(ShelfHelper.testShelvesCount))
            .andReturn();

    Page<ShelfResponse> shelvesResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          new TypeReference<RestPageImpl<ShelfResponse>>() {}
    );

    assertFalse(shelvesResponse.isEmpty());
    assertEquals(ShelfHelper.testShelvesCount, shelvesResponse.getTotalElements());
    assertEquals(2, shelvesResponse.getTotalPages());
    assertEquals(5, shelvesResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(shelvesResponse.getContent().get(i))
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(shelfMapper.mapEntityToResponse(shelfList.get(i)));
    }
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllShelvesWithoutParameters_shelvesList() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));

    var response = mockMvc.perform(get(ShelfHelper.shelfUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(ShelfHelper.testShelvesCount)))
            .andReturn();

    List<ShelfResponse> shelvesResponse = mapper.readValue(
            response.getResponse().getContentAsString(),
            new TypeReference<ArrayList<ShelfResponse>>() {}
    );

    assertFalse(shelvesResponse.isEmpty());
    assertEquals(ShelfHelper.testShelvesCount, shelvesResponse.size());

    for (int i = 0; i < ShelfHelper.testShelvesCount; i++) {
      AssertionsForClassTypes.assertThat(shelvesResponse.get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(shelfMapper.mapEntityToResponse(shelfList.get(i)));
    }
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllShelvesWithoutParameters_empty() throws Exception {
    var response = mockMvc
            .perform(get(ShelfHelper.shelfUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)))
            .andReturn();

    List<ShelfResponse> shelvesResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          new TypeReference<ArrayList<ShelfResponse>>() {}
    );

    assertTrue(shelvesResponse.isEmpty());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllShelvesWithParametersPageAndSize_empty() throws Exception {
    var response = mockMvc
            .perform(get(ShelfHelper.getShelfUrlPathWithPageAndSize())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andExpect(jsonPath("$['content']", hasSize(0)))
            .andReturn();

    Page<ShelfResponse> shelvesResponse = mapper.readValue(
            response.getResponse().getContentAsString(),
            new TypeReference<RestPageImpl<ShelfResponse>>() {}
    );

    assertTrue(shelvesResponse.isEmpty());
    assertEquals(0, shelvesResponse.getTotalElements());
    assertEquals(0, shelvesResponse.getTotalPages());
    assertEquals(0, shelvesResponse.getContent().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllShelves_customPageRequest() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));
    Page<Shelf> expectedShelfList =
          shelfRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending()));

    var response = mockMvc
          .perform(get(createUrlPathGetPageable(ShelfHelper.shelfUrlPath, 1, 3, "id", false))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$['pageable']['paged']").value("true"))
          .andReturn();

    Page<ShelfResponse> shelvesResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          new TypeReference<RestPageImpl<ShelfResponse>>() {}
    );

    assertFalse(shelvesResponse.isEmpty());
    assertEquals(expectedShelfList.getTotalElements(), shelvesResponse.getTotalElements());
    assertEquals(expectedShelfList.getTotalPages(), shelvesResponse.getTotalPages());
    assertEquals(expectedShelfList.getContent().size(), shelvesResponse.getContent().size());

    for (int i = 0; i < expectedShelfList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(shelvesResponse.getContent().get(i))
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(shelfMapper.mapEntityToResponse(expectedShelfList.getContent().get(i)));
    }
  }
}
