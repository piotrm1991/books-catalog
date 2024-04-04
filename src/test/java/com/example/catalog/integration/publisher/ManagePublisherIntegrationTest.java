package com.example.catalog.integration.publisher;


import static com.example.catalog.util.ErrorMessagesConstants.PublisherNameAlreadyExists;
import static com.example.catalog.util.ErrorMessagesConstants.PublisherNameCanNotBeBlank;
import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.mapper.PublisherMapper;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.transaction.Transactional;
import java.util.List;

public class ManagePublisherIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private PublisherRepository publisherRepository;

  @Autowired
  private PublisherMapper publisherMapper;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  public void givenCorrectPublisherCreate_whenCreatePublisher_thenCorrect() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(PublisherHelper.publisherUrlPath)
            .content(mapper.writeValueAsString(PublisherHelper.createPublisherCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    PublisherResponse publisherResponse = mapper.readValue(response.getResponse().getContentAsString(), PublisherResponse.class);

    assertEquals(PublisherHelper.name, publisherResponse.name());
    assertEquals(1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectPublisherCreateExistingName_whenCreatePublisher_thenException() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(PublisherHelper.publisherUrlPath)
            .content(mapper.writeValueAsString(PublisherHelper.createPublisherCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(PublisherHelper.publisherUrlPath)
            .content(mapper.writeValueAsString(PublisherHelper.createPublisherCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(PublisherNameAlreadyExists));
    assertEquals(1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectPublisherCreateBlankName_whenCreatePublisher_thenException() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
                    .post(PublisherHelper.publisherUrlPath)
                    .content(mapper.writeValueAsString(PublisherHelper.createEmptyPublisherCreate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(PublisherNameCanNotBeBlank));
    assertEquals(0, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenCorrectPublisherUpdate_whenUpdatePublisher_thenCorrect() throws Exception {
    Publisher publisher = publisherRepository.save(PublisherHelper.createPublisher());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(PublisherHelper.publisherUrlPath, publisher.getId()))
                    .content(mapper.writeValueAsString(PublisherHelper.createPublisherUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    PublisherResponse publisherResponse = mapper.readValue(response.getResponse().getContentAsString(), PublisherResponse.class);

    assertEquals(publisher.getId(), publisherResponse.id());
    assertEquals(PublisherHelper.nameUpdated, publisherResponse.name());
    assertEquals(PublisherHelper.nameUpdated, publisherRepository.findById(publisher.getId()).get().getName());
    assertEquals(1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectPublisherUpdateBlankName_whenUpdatePublisher_thenException() throws Exception {
    Publisher publisher = publisherRepository.save(PublisherHelper.createPublisher());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(PublisherHelper.publisherUrlPath, publisher.getId()))
                    .content(mapper.writeValueAsString(PublisherHelper.createPublisherUpdateBlankName()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(PublisherNameCanNotBeBlank));
    assertEquals(1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectPublisherUpdateNameAlreadyExists_whenUpdatePublisher_thenException() throws Exception {
    Publisher publisher = publisherRepository.save(PublisherHelper.createPublisher());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(PublisherHelper.publisherUrlPath, publisher.getId()))
                    .content(mapper.writeValueAsString(PublisherHelper.createPublisherUpdateWithExistingName()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(PublisherNameAlreadyExists));
    assertEquals(1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectPublisherUpdateIdNotExists_whenUpdatePublisher_thenException() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(PublisherHelper.publisherUrlPath, invalidId))
                    .content(mapper.writeValueAsString(PublisherHelper.createPublisherUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Publisher.class.getSimpleName(), invalidId), errorMessage);
  }

  @Test
  @Transactional
  public void givenCorrectId_whenDeletePublisher_thenCorrect() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));
    Publisher publisher = publisherRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(PublisherHelper.publisherUrlPath, publisher.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(PublisherHelper.testPublishersCount-1, publisherRepository.findAll().size());
  }

  @Test
  @Transactional
  public void givenIncorrectId_whenDeletePublisher_thenException() throws Exception {
    List<Publisher> publisherList = PublisherHelper.preparePublisherList();
    publisherList.forEach(a -> publisherRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(PublisherHelper.publisherUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Publisher.class.getSimpleName(), invalidId), errorMessage);
    assertEquals(PublisherHelper.testPublishersCount, publisherRepository.findAll().size());
  }
}
