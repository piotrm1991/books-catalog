package com.example.catalog.author.mapper;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.response.AuthorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {

  @InjectMocks
  private AuthorMapper authorMapper;

  @Mock
  private ObjectMapper mapper;

  private Author author;
  private AuthorCreate authorCreate;
  private AuthorResponse authorResponse;

  @BeforeEach
  void setUp() {
    author = AuthorHelper.createAuthor();
    authorCreate = AuthorHelper.createAuthorCreate();
    authorResponse = AuthorHelper.createAuthorResponse();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(authorCreate), Author.class)).thenReturn(author);

    Author expectedLeave = authorMapper.mapAuthorCreateToEntity(authorCreate);

    assertEquals(expectedLeave, author);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(author), AuthorResponse.class)).thenReturn(authorResponse);

    AuthorResponse expectedLeaveResponse = authorMapper.mapEntityToResponse(author);

    assertEquals(expectedLeaveResponse, authorResponse);
  }
}