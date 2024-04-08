package com.example.catalog.room.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.response.RoomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomMapperTest {

  @InjectMocks
  private RoomMapper roomMapper;

  @Mock
  private ObjectMapper mapper;

  private Room room;
  private RoomCreate roomCreate;
  private RoomResponse roomResponse;

  @BeforeEach
  void setUp() {
    room = RoomHelper.createRoom();
    roomCreate = RoomHelper.createRoomCreate();
    roomResponse = RoomHelper.createRoomResponse();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(roomCreate), Room.class)).thenReturn(room);

    Room expectedRoom = roomMapper.mapRoomCreateToEntity(roomCreate);

    assertEquals(expectedRoom, room);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(room), RoomResponse.class))
          .thenReturn(roomResponse);

    RoomResponse expectedRoomResponse = roomMapper.mapEntityToResponse(room);

    assertEquals(expectedRoomResponse, roomResponse);
  }
}