package com.example.catalog.statustype.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatusTypeMapperTest {

  @InjectMocks
  private StatusTypeMapper statusTypeMapper;

  @Mock
  private ObjectMapper mapper;

  private StatusType statusType;
  private StatusTypeCreate statusTypeCreate;
  private StatusTypeResponse statusTypeResponse;

  @BeforeEach
  void setUp() {
    statusType = StatusTypeHelper.createStatusType();
    statusTypeCreate = StatusTypeHelper.createStatusTypeCreate();
    statusTypeResponse = StatusTypeHelper.createStatusTypeResponse();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(statusTypeCreate), StatusType.class))
          .thenReturn(statusType);

    StatusType expectedStatusType = statusTypeMapper.mapStatusTypeCreateToEntity(statusTypeCreate);

    assertEquals(expectedStatusType, statusType);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(statusType), StatusTypeResponse.class))
          .thenReturn(statusTypeResponse);

    StatusTypeResponse expectedStatusTypeResponse =
          statusTypeMapper.mapEntityToResponse(statusType);

    assertEquals(expectedStatusTypeResponse, statusTypeResponse);
  }
}