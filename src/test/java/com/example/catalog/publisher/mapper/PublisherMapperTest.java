package com.example.catalog.publisher.mapper;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.publisher.PublisherHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherMapperTest {

  @InjectMocks
  private PublisherMapper publisherMapper;

  @Mock
  private ObjectMapper mapper;

  private Publisher publisher;
  private PublisherCreate publisherCreate;
  private PublisherResponse publisherResponse;

  @BeforeEach
  void setUp() {
    publisher = PublisherHelper.createPublisher();
    publisherCreate = PublisherHelper.createPublisherCreate();
    publisherResponse = PublisherHelper.createPublisherResponse();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(publisherCreate), Publisher.class)).thenReturn(publisher);

    Publisher expectedLeave = publisherMapper.mapPublisherCreateToEntity(publisherCreate);

    assertEquals(expectedLeave, publisher);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(publisher), PublisherResponse.class)).thenReturn(publisherResponse);

    PublisherResponse expectedLeaveResponse = publisherMapper.mapEntityToResponse(publisher);

    assertEquals(expectedLeaveResponse, publisherResponse);
  }
}