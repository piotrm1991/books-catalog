package com.example.catalog.integration.publisher;

import static com.example.catalog.util.MessagesConstants.createEntityNotExistsMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.mapper.PublisherMapper;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.publisher.response.PublisherResponse;
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
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

public class ViewPublishersIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private PublisherRepository publisherRepository;

  @Autowired
  private PublisherMapper publisherMapper;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenGetPublisherById_theReturnPublisherResponseCorrect() throws Exception {
    Publisher expectedPublisher = PublisherHelper.createPublisher();
    expectedPublisher = publisherRepository.save(expectedPublisher);

    var response = mockMvc.perform(get(createUrlPathWithId(PublisherHelper.publisherUrlPath, expectedPublisher.getId()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    PublisherResponse publisherResponse = mapper.readValue(response.getResponse().getContentAsString(), PublisherResponse.class);

    assertEquals(expectedPublisher.getId(), publisherResponse.id());
    assertEquals(expectedPublisher.getName(), publisherResponse.name());

    assertThat(publisherRepository.getReferenceById(expectedPublisher.getId())).isEqualTo(expectedPublisher);
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(get(createUrlPathWithId(PublisherHelper.publisherUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains(createEntityNotExistsMessage(Publisher.class.getSimpleName(), invalidId)));
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllPublishers_defaultPageRequest() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));

    var response = mockMvc.perform(get(PublisherHelper.publisherUrlPath)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<PublisherResponse> publishersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<PublisherResponse>>() {});

    assertFalse(publishersResponse.isEmpty());
    assertEquals( PublisherHelper.testPublishersCount, publishersResponse.getTotalElements());
    assertEquals(4, publishersResponse.getTotalPages());
    assertEquals(5, publishersResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(publishersResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(publisherMapper.mapEntityToResponse(publisherList.get(i)));
    }
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllPublishers_empty() throws Exception {
    var response = mockMvc.perform(get(PublisherHelper.publisherUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<PublisherResponse> publishersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<PublisherResponse>>() {});

    assertTrue(publishersResponse.isEmpty());
    assertEquals( 0, publishersResponse.getTotalElements());
    assertEquals(0, publishersResponse.getTotalPages());
    assertEquals(0, publishersResponse.getContent().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllPublishers_customPageRequest() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));
    Page<Publisher> expectedPublisherList = publisherRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

    var response = mockMvc.perform(get(createUrlPathGetPageable(PublisherHelper.publisherUrlPath, 1, 3, "id", false))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<PublisherResponse> publishersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<PublisherResponse>>() {});

    assertFalse(publishersResponse.isEmpty());
    assertEquals(expectedPublisherList.getTotalElements(), publishersResponse.getTotalElements());
    assertEquals(expectedPublisherList.getTotalPages(), publishersResponse.getTotalPages());
    assertEquals(expectedPublisherList.getContent().size(), publishersResponse.getContent().size());

    for (int i = 0; i < expectedPublisherList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(publishersResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(publisherMapper.mapEntityToResponse(expectedPublisherList.getContent().get(i)));
    }
  }
}
