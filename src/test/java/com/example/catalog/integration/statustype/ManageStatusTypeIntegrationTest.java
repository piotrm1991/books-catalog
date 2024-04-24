package com.example.catalog.integration.statustype;

import static com.example.catalog.util.MessagesConstants.StatusTypeNameAlreadyExistsMessage;
import static com.example.catalog.util.MessagesConstants.StatusTypeNameCanNotBeBlankMessage;
import static com.example.catalog.util.MessagesConstants.createEntityNotExistsMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration tests for POST, PUT, DELETE operations on StatusType entity.
 */
public class ManageStatusTypeIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private StatusTypeRepository statusTypeRepository;

  private final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectStatusTypeCreate_whenCreateStatusType_thenCorrect() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(StatusTypeHelper.statusTypeUrlPath)
            .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    StatusTypeResponse statusTypeResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          StatusTypeResponse.class
    );

    assertEquals(StatusTypeHelper.name, statusTypeResponse.name());
    assertEquals(1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectStatusTypeCreateExistingName_whenCreateStatusType_thenException()
        throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .post(StatusTypeHelper.statusTypeUrlPath)
            .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(StatusTypeHelper.statusTypeUrlPath)
            .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(StatusTypeNameAlreadyExistsMessage));
    assertEquals(1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectStatusTypeCreateBlankName_whenCreateStatusType_thenException()
        throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .post(StatusTypeHelper.statusTypeUrlPath)
                    .content(mapper.writeValueAsString(
                          StatusTypeHelper.createEmptyStatusTypeCreate())
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(StatusTypeNameCanNotBeBlankMessage));
    assertEquals(0, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectStatusTypeUpdate_whenUpdateStatusType_thenCorrect() throws Exception {
    StatusType statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(
                          StatusTypeHelper.statusTypeUrlPath,
                          statusType.getId())
                    )
                    .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    StatusTypeResponse statusTypeResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          StatusTypeResponse.class
    );

    assertEquals(statusType.getId(), statusTypeResponse.id());
    assertEquals(StatusTypeHelper.nameUpdated, statusTypeResponse.name());
    assertEquals(
          StatusTypeHelper.nameUpdated,
          statusTypeRepository.findById(statusType.getId()).get().getName()
    );
    assertEquals(1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectStatusTypeUpdateBlankName_whenUpdateStatusType_thenException()
        throws Exception {

    StatusType statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(
                          StatusTypeHelper.statusTypeUrlPath,
                          statusType.getId())
                    )
                    .content(mapper.writeValueAsString(
                          StatusTypeHelper.createStatusTypeUpdateBlankName())
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(StatusTypeNameCanNotBeBlankMessage));
    assertEquals(1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectStatusTypeUpdateNameAlreadyExists_whenUpdateStatusType_thenException()
        throws Exception {

    StatusType statusType = statusTypeRepository.save(StatusTypeHelper.createStatusType());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(
                          StatusTypeHelper.statusTypeUrlPath,
                          statusType.getId())
                    )
                    .content(mapper.writeValueAsString(
                          StatusTypeHelper.createStatusTypeUpdateWithExistingName())
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(StatusTypeNameAlreadyExistsMessage));
    assertEquals(1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectStatusTypeUpdateIdNotExists_whenUpdateStatusType_thenException()
        throws Exception {

    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(StatusTypeHelper.statusTypeUrlPath, invalidId))
                    .content(mapper.writeValueAsString(StatusTypeHelper.createStatusTypeUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
          createEntityNotExistsMessage(StatusType.class.getSimpleName(), invalidId),
          errorMessage
    );
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenDeleteStatusType_thenCorrect() throws Exception {
    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));
    StatusType statusType = statusTypeRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(StatusTypeHelper.statusTypeUrlPath, statusType.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(StatusTypeHelper.testStatusTypesCount - 1, statusTypeRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenDeleteStatusType_thenException() throws Exception {
    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(StatusTypeHelper.statusTypeUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
          createEntityNotExistsMessage(StatusType.class.getSimpleName(), invalidId),
          errorMessage
    );
    assertEquals(StatusTypeHelper.testStatusTypesCount, statusTypeRepository.findAll().size());
  }
}
