package com.example.catalog.testentity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.catalog.shared.motherobject.response.TestEntityResponseDtoMother;
import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.repository.TestEntityRepository;
import com.example.catalog.testentity.request.TestEntityRequestDto;
import com.example.catalog.shared.motherobject.entity.TestEntityMother;
import com.example.catalog.testentity.mapper.TestEntityMapper;
import com.example.catalog.shared.motherobject.request.TestEntityRequestDtoMother;
import com.example.catalog.testentity.response.TestEntityResponseDto;
import com.example.catalog.testentity.validator.TestEntityValidator;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TestEntityServiceTest {

  @InjectMocks
  private TestEntityService testEntityService;
  @Mock
  private TestEntityRepository testEntityRepository;
  @Mock
  private TestEntityValidator testEntityValidator;
  @Mock
  private TestEntityMapper testEntityMapper;

  private static final TestEntityResponseDto responseDto = TestEntityResponseDtoMother.base()
      .build();
  private static final TestEntity entity = TestEntityMother.base().build();
  private static final TestEntityRequestDto requestDto = TestEntityRequestDtoMother.base().build();


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    when(testEntityRepository.findById(any())).thenReturn(Optional.ofNullable(entity));
    when(testEntityRepository.save(any())).thenReturn(entity);
    when(testEntityMapper.mapToEntity(any())).thenReturn(entity);
  }

  @Test
  void testGetById() {
    TestEntity actualResult = testEntityService.getById(1L);

    assertThat(actualResult).isEqualTo(entity);
  }

  @Test
  void testGetByIdThrowsEntityNotFoundException() {
    when(testEntityRepository.findById(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> testEntityService.getById(1L)).isInstanceOf(
        EntityNotFoundException.class);
  }

  @Test
  void testCreate() {
    TestEntity actualResult = testEntityService.create(requestDto);

    assertThat(actualResult).isEqualTo(entity);
  }
}