package com.example.catalog.room.impl;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.mapper.RoomMapper;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;
import com.example.catalog.room.service.impl.RoomServiceImpl;
import com.example.catalog.room.RoomHelper;
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
class RoomServiceImplTest {

  @InjectMocks
  private RoomServiceImpl roomService;

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private RoomMapper roomMapper;

  private Room room;
  private RoomCreate roomCreate;
  private RoomUpdate roomUpdate;
  private RoomResponse roomResponse;

  @BeforeEach
  void setUp() {
    room = RoomHelper.createRoom();
    roomCreate = RoomHelper.createRoomCreate();
    roomUpdate = RoomHelper.createRoomUpdate();
    roomResponse = RoomHelper.createRoomResponse();
  }

  @Test
  void givenCorrectRoomRequest_whenCreateRoom_thenCorrect() {
    when(roomMapper.mapRoomCreateToEntity(roomCreate)).thenReturn(room);
    when(roomMapper.mapEntityToResponse(room)).thenReturn(roomResponse);
    when(roomRepository.save(any(Room.class))).thenReturn(room);

    RoomResponse actualRoomResponse = roomService.createRoom(roomCreate);

    assertEquals(roomResponse, actualRoomResponse);
    verify(roomRepository, times(1)).save(room);
  }

  @Test
  void givenCorrectRoomRequest_whenUpdateRoom_thenCorrect() {
    when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
    when(roomRepository.save(any(Room.class))).thenReturn(room);
    when(roomMapper.mapEntityToResponse(any(Room.class))).thenReturn(roomResponse);

    RoomResponse actualRoomResponse = roomService.updateRoom(RoomHelper.id, roomUpdate);

    assertEquals(roomResponse, actualRoomResponse);
    verify(roomRepository, times(1)).save(room);
  }

  @Test
  void givenCorrectId_whenGetRoomById_thenCorrect() {
    when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

    Room actualRoom = roomService.getRoomById(anyLong());

    assertEquals(room, actualRoom);
  }

  @Test
  void givenIncorrectId_whenGetRoomById_thenException() {
    Long invalidId = 1L;

    when(roomRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> roomService.getRoomById(invalidId));
    String expectedMessage = "Room with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeleteRoomById_thenCorrect() {
    when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

    roomService.deleteRoomById(anyLong());

    verify(roomRepository, times(1)).delete(room);
  }

  @Test
  void givenIncorrectId_whenDeleteRoomById_thenException() {
    Long invalidId = 1L;

    when(roomRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> roomService.deleteRoomById(invalidId));
    String expectedMessage = "Room with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllRoomsPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(roomRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = roomService.getAllRoomsPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetRoomResponseById_thenCorrect() {
    when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
    when(roomMapper.mapEntityToResponse(room)).thenReturn(roomResponse);

    RoomResponse actualRoomResponse = roomService.getRoomResponseById(anyLong());

    assertEquals(roomResponse, actualRoomResponse);
  }

  @Test
  void givenIncorrectId_whenGetRoomResponseById_thenException() {
    Long invalidId = 1L;

    when(roomRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> roomService.getRoomResponseById(invalidId));
    String expectedMessage = "Room with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}