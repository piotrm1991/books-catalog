package com.example.catalog.publisher.impl;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.mapper.PublisherMapper;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

  @InjectMocks
  private PublisherServiceImpl publisherService;

  @Mock
  private PublisherRepository publisherRepository;

  @Mock
  private PublisherMapper publisherMapper;

  private Publisher publisher;
  private PublisherCreate publisherCreate;
  private PublisherUpdate publisherUpdate;
  private PublisherResponse publisherResponse;

  @BeforeEach
  void setUp() {
    publisher = PublisherHelper.createPublisher();
    publisherCreate = PublisherHelper.createPublisherCreate();
    publisherUpdate = PublisherHelper.createPublisherUpdate();
    publisherResponse = PublisherHelper.createPublisherResponse();
  }

  @Test
  void givenCorrectPublisherRequest_whenCreatePublisher_thenCorrect() {
    when(publisherMapper.mapPublisherCreateToEntity(publisherCreate)).thenReturn(publisher);
    when(publisherMapper.mapEntityToResponse(publisher)).thenReturn(publisherResponse);
    when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

    PublisherResponse actualPublisherResponse = publisherService.createPublisher(publisherCreate);

    assertEquals(publisherResponse, actualPublisherResponse);
    verify(publisherRepository, times(1)).save(publisher);
  }

  @Test
  void givenCorrectPublisherRequest_whenUpdatePublisher_thenCorrect() {
    when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
    when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);
    when(publisherMapper.mapEntityToResponse(any(Publisher.class))).thenReturn(publisherResponse);

    PublisherResponse actualPublisherResponse = publisherService.updatePublisher(PublisherHelper.id, publisherUpdate);

    assertEquals(publisherResponse, actualPublisherResponse);
    verify(publisherRepository, times(1)).save(publisher);
  }

  @Test
  void givenCorrectId_whenGetPublisherById_thenCorrect() {
    when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));

    Publisher actualPublisher = publisherService.getPublisherById(anyLong());

    assertEquals(publisher, actualPublisher);
  }

  @Test
  void givenIncorrectId_whenGetPublisherById_thenException() {
    Long invalidId = 1L;

    when(publisherRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> publisherService.getPublisherById(invalidId));
    String expectedMessage = "Publisher with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeletePublisherById_thenCorrect() {
    when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));

    publisherService.deletePublisherById(anyLong());

    verify(publisherRepository, times(1)).delete(publisher);
  }

  @Test
  void givenIncorrectId_whenDeletePublisherById_thenException() {
    Long invalidId = 1L;

    when(publisherRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> publisherService.deletePublisherById(invalidId));
    String expectedMessage = "Publisher with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllPublishersPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(publisherRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = publisherService.getAllPublishersPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetPublisherResponseById_thenCorrect() {
    when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
    when(publisherMapper.mapEntityToResponse(publisher)).thenReturn(publisherResponse);

    PublisherResponse actualPublisherResponse = publisherService.getPublisherResponseById(anyLong());

    assertEquals(publisherResponse, actualPublisherResponse);
  }

  @Test
  void givenIncorrectId_whenGetPublisherResponseById_thenException() {
    Long invalidId = 1L;

    when(publisherRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> publisherService.getPublisherResponseById(invalidId));
    String expectedMessage = "Publisher with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}