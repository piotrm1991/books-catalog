package com.example.catalog.statustype.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.mapper.StatusTypeMapper;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.example.catalog.statustype.service.impl.StatusTypeServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class StatusTypeServiceImplTest {

  @InjectMocks
  private StatusTypeServiceImpl statusTypeService;

  @Mock
  private StatusTypeRepository statusTypeRepository;

  @Mock
  private StatusTypeMapper statusTypeMapper;

  private StatusType statusType;
  private StatusTypeCreate statusTypeCreate;
  private StatusTypeUpdate statusTypeUpdate;
  private StatusTypeResponse statusTypeResponse;

  @BeforeEach
  void setUp() {
    statusType = StatusTypeHelper.createStatusType();
    statusTypeCreate = StatusTypeHelper.createStatusTypeCreate();
    statusTypeUpdate = StatusTypeHelper.createStatusTypeUpdate();
    statusTypeResponse = StatusTypeHelper.createStatusTypeResponse();
  }

  @Test
  void givenCorrectStatusTypeRequest_whenCreateStatusType_thenCorrect() {
    when(statusTypeMapper.mapStatusTypeCreateToEntity(statusTypeCreate)).thenReturn(statusType);
    when(statusTypeMapper.mapEntityToResponse(statusType)).thenReturn(statusTypeResponse);
    when(statusTypeRepository.save(any(StatusType.class))).thenReturn(statusType);

    StatusTypeResponse actualStatusTypeResponse =
          statusTypeService.createStatusType(statusTypeCreate);

    assertEquals(statusTypeResponse, actualStatusTypeResponse);
    verify(statusTypeRepository, times(1)).save(statusType);
  }

  @Test
  void givenCorrectStatusTypeRequest_whenUpdateStatusType_thenCorrect() {
    when(statusTypeRepository.findById(anyLong())).thenReturn(Optional.of(statusType));
    when(statusTypeRepository.save(any(StatusType.class))).thenReturn(statusType);
    when(statusTypeMapper.mapEntityToResponse(any(StatusType.class)))
          .thenReturn(statusTypeResponse);

    StatusTypeResponse actualStatusTypeResponse =
          statusTypeService.updateStatusType(StatusTypeHelper.id, statusTypeUpdate);

    assertEquals(statusTypeResponse, actualStatusTypeResponse);
    verify(statusTypeRepository, times(1)).save(statusType);
  }

  @Test
  void givenCorrectId_whenGetStatusTypeById_thenCorrect() {
    when(statusTypeRepository.findById(anyLong())).thenReturn(Optional.of(statusType));

    StatusType actualStatusType = statusTypeService.getStatusTypeById(anyLong());

    assertEquals(statusType, actualStatusType);
  }

  @Test
  void givenIncorrectId_whenGetStatusTypeById_thenException() {
    Long invalidId = 1L;

    when(statusTypeRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> statusTypeService.getStatusTypeById(invalidId));
    String expectedMessage = "StatusType with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeleteStatusTypeById_thenCorrect() {
    when(statusTypeRepository.findById(anyLong())).thenReturn(Optional.of(statusType));

    statusTypeService.deleteStatusTypeById(anyLong());

    verify(statusTypeRepository, times(1)).delete(statusType);
  }

  @Test
  void givenIncorrectId_whenDeleteStatusTypeById_thenException() {
    Long invalidId = 1L;

    when(statusTypeRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> statusTypeService.deleteStatusTypeById(invalidId));
    String expectedMessage = "StatusType with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllStatusTypesPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(statusTypeRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = statusTypeService.getAllStatusTypesPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetStatusTypeResponseById_thenCorrect() {
    when(statusTypeRepository.findById(anyLong())).thenReturn(Optional.of(statusType));
    when(statusTypeMapper.mapEntityToResponse(statusType)).thenReturn(statusTypeResponse);

    StatusTypeResponse actualStatusTypeResponse =
          statusTypeService.getStatusTypeResponseById(anyLong());

    assertEquals(statusTypeResponse, actualStatusTypeResponse);
  }

  @Test
  void givenIncorrectId_whenGetStatusTypeResponseById_thenException() {
    Long invalidId = 1L;

    when(statusTypeRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> statusTypeService.getStatusTypeResponseById(invalidId));
    String expectedMessage = "StatusType with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}