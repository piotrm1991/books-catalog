package com.example.catalog.integration.statustype;

import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.mapper.StatusTypeMapper;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.example.catalog.statustype.response.StatusTypeResponse;
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

import static com.example.catalog.util.ErrorMessagesConstants.createStatusTypeNotExistMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ViewStatusTypeIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private StatusTypeRepository statusTypeRepository;

  @Autowired
  private StatusTypeMapper statusTypeMapper;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  public void givenCorrectId_whenGetStatusTypeById_theReturnStatusTypeResponseCorrect() throws Exception {
    StatusType expectedStatusType = StatusTypeHelper.createStatusType();
    expectedStatusType = statusTypeRepository.save(expectedStatusType);

    var response = mockMvc.perform(get(createUrlPathWithId(StatusTypeHelper.statusTypeUrlPath, expectedStatusType.getId()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    StatusTypeResponse statusTypeResponse = mapper.readValue(response.getResponse().getContentAsString(), StatusTypeResponse.class);

    assertEquals(expectedStatusType.getId(), statusTypeResponse.id());
    assertEquals(expectedStatusType.getName(), statusTypeResponse.name());

    assertThat(statusTypeRepository.getReferenceById(expectedStatusType.getId())).isEqualTo(expectedStatusType);
  }

  @Test
  @Transactional
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(get(createUrlPathWithId(StatusTypeHelper.statusTypeUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains(createStatusTypeNotExistMessage(invalidId)));

  }

  @Test
  @Transactional
  public void testGetAllStatusTypes_defaultPageRequest() throws Exception {
    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));

    var response = mockMvc.perform(get(StatusTypeHelper.statusTypeUrlPath)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<StatusTypeResponse> statusTypesResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<StatusTypeResponse>>() {});

    assertFalse(statusTypesResponse.isEmpty());
    assertEquals( StatusTypeHelper.testStatusTypesCount, statusTypesResponse.getTotalElements());
    assertEquals(2, statusTypesResponse.getTotalPages());
    assertEquals(5, statusTypesResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(statusTypesResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(statusTypeMapper.mapEntityToResponse(statusTypeList.get(i)));
    }
  }

  @Test
  @Transactional
  public void testGetAllStatusTypes_empty() throws Exception {
    var response = mockMvc.perform(get(StatusTypeHelper.statusTypeUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<StatusTypeResponse> statusTypesResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<StatusTypeResponse>>() {});

    assertTrue(statusTypesResponse.isEmpty());
    assertEquals( 0, statusTypesResponse.getTotalElements());
    assertEquals(0, statusTypesResponse.getTotalPages());
    assertEquals(0, statusTypesResponse.getContent().size());
  }

  @Test
  @Transactional
  public void testGetAllStatusTypes_customPageRequest() throws Exception {
    List<StatusType> statusTypeList = StatusTypeHelper.prepareStatusTypeList();
    statusTypeList.forEach(a -> statusTypeRepository.save(a));
    Page<StatusType> expectedStatusTypeList = statusTypeRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

    var response = mockMvc.perform(get(createUrlPathGetPageable(StatusTypeHelper.statusTypeUrlPath, 1, 3, "id", false))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<StatusTypeResponse> statusTypesResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<StatusTypeResponse>>() {});

    assertFalse(statusTypesResponse.isEmpty());
    assertEquals(expectedStatusTypeList.getTotalElements(), statusTypesResponse.getTotalElements());
    assertEquals(expectedStatusTypeList.getTotalPages(), statusTypesResponse.getTotalPages());
    assertEquals(expectedStatusTypeList.getContent().size(), statusTypesResponse.getContent().size());

    for (int i = 0; i < expectedStatusTypeList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(statusTypesResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(statusTypeMapper.mapEntityToResponse(expectedStatusTypeList.getContent().get(i)));
    }
  }
}
