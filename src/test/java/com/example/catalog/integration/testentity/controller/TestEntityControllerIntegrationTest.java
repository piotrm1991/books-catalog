package com.example.catalog.integration.testentity.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.shared.motherobject.response.TestEntityResponseDtoMother;
import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.repository.TestEntityRepository;
import com.example.catalog.testentity.request.TestEntityRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.motherobject.entity.TestEntityMother;
import com.example.catalog.shared.motherobject.request.TestEntityRequestDtoMother;
import com.example.catalog.testentity.response.TestEntityResponseDto;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class TestEntityControllerIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private TestEntityRepository repository;

  @Autowired
  private ObjectMapper mapper;

  private static final String URL = "/test-entities";

  private final TestEntityRequestDto requestDto = TestEntityRequestDtoMother.base().build();
  private static final TestEntityResponseDto responseDto = TestEntityResponseDtoMother.base()
      .build();
  private static final TestEntity entity = TestEntityMother.base().build();

  @Test
  @Transactional
    //TODO: add negative result tests
  void testGetById() throws Exception {
    TestEntity expectedEntity = repository.save(entity);

    var response = mockMvc.perform(get(URL + "/" + expectedEntity.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    TestEntity entity = mapper.readValue(
        response.getResponse().getContentAsByteArray(),
        TestEntity.class
    );

    assertEquals(expectedEntity.getId(), entity.getId());
    assertEquals(expectedEntity.getName(), entity.getName());

    Assertions.assertThat(repository.getReferenceById(expectedEntity.getId())).isEqualTo(
            expectedEntity);
  }

  @Test
  @Transactional
    //TODO: add negative result tests
  void testCreate() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(requestDto)))
        .andExpectAll(
            status().isCreated(),
            jsonPath("$.name").value(responseDto.getName())
        )
        .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    TestEntityResponseDto responseDto = mapper.readValue(responseBody, TestEntityResponseDto.class);

    assertThat(repository.count()).isEqualTo(1);
    Assertions.assertThat(
            repository.getReferenceById(responseDto.getId()).getName()).isEqualTo(
            entity.getName());
  }
}