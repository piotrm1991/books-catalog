package com.example.catalog.shelf.impl;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.service.RoomService;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.mapper.ShelfMapper;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import com.example.catalog.shelf.service.impl.ShelfServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelfServiceImplTest {

  @InjectMocks
  private ShelfServiceImpl shelfService;

  @Mock
  private ShelfRepository shelfRepository;

  @Mock
  private ShelfMapper shelfMapper;

  @Mock
  private RoomService roomService;

  private Shelf shelf;
  private ShelfCreate shelfCreate;
  private ShelfUpdate shelfUpdate;
  private ShelfResponse shelfResponse;

  @BeforeEach
  void setUp() {
    shelf = ShelfHelper.createShelf();
    shelfCreate = ShelfHelper.createShelfCreate();
    shelfUpdate = ShelfHelper.createShelfUpdate();
    shelfResponse = ShelfHelper.createShelfResponse();
  }

  @Test
  void givenCorrectShelfRequest_whenCreateShelf_thenCorrect() {
    when(roomService.getRoomById(RoomHelper.id)).thenReturn(RoomHelper.createRoom());
    when(shelfMapper.mapShelfCreateToEntity(shelfCreate)).thenReturn(shelf);
    when(shelfMapper.mapEntityToResponse(shelf)).thenReturn(shelfResponse);
    when(shelfRepository.save(any(Shelf.class))).thenReturn(shelf);

    ShelfResponse actualShelfResponse = shelfService.createShelf(shelfCreate);

    assertEquals(shelfResponse, actualShelfResponse);
    verify(shelfRepository, times(1)).save(shelf);
  }

  @Test
  void givenCorrectShelfRequest_whenUpdateShelf_thenCorrect() {
    when(shelfRepository.findById(ShelfHelper.id)).thenReturn(Optional.of(shelf));
    when(shelfMapper.mapShelfUpdateToEntity(shelf, shelfUpdate)).thenReturn(shelf);
    when(shelfRepository.save(any(Shelf.class))).thenReturn(shelf);
    when(shelfMapper.mapEntityToResponse(any(Shelf.class))).thenReturn(shelfResponse);

    ShelfResponse actualShelfResponse = shelfService.updateShelf(ShelfHelper.id, shelfUpdate);

    assertEquals(shelfResponse, actualShelfResponse);
    verify(shelfRepository, times(1)).save(shelf);
  }

  @Test
  void givenCorrectId_whenGetShelfById_thenCorrect() {
    when(shelfRepository.findById(anyLong())).thenReturn(Optional.of(shelf));

    Shelf actualShelf = shelfService.getShelfById(anyLong());

    assertEquals(shelf, actualShelf);
  }

  @Test
  void givenIncorrectId_whenGetShelfById_thenException() {
    Long invalidId = 1L;

    when(shelfRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> shelfService.getShelfById(invalidId));
    String expectedMessage = "Shelf with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeleteShelfById_thenCorrect() {
    when(shelfRepository.findById(anyLong())).thenReturn(Optional.of(shelf));

    shelfService.deleteShelfById(anyLong());

    verify(shelfRepository, times(1)).delete(shelf);
  }

  @Test
  void givenIncorrectId_whenDeleteShelfById_thenException() {
    Long invalidId = 1L;

    when(shelfRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> shelfService.deleteShelfById(invalidId));
    String expectedMessage = "Shelf with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllShelvesPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(shelfRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = shelfService.getAllShelvesPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetShelfResponseById_thenCorrect() {
    when(shelfRepository.findById(anyLong())).thenReturn(Optional.of(shelf));
    when(shelfMapper.mapEntityToResponse(shelf)).thenReturn(shelfResponse);

    ShelfResponse actualShelfResponse = shelfService.getShelfResponseById(anyLong());

    assertEquals(shelfResponse, actualShelfResponse);
  }

  @Test
  void givenIncorrectId_whenGetShelfResponseById_thenException() {
    Long invalidId = 1L;

    when(shelfRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> shelfService.getShelfResponseById(invalidId));
    String expectedMessage = "Shelf with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}