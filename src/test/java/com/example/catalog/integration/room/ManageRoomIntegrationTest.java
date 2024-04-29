package com.example.catalog.integration.room;

import static com.example.catalog.util.ExceptionMessagesConstants.createEntityNotExistsMessage;
import static com.example.catalog.util.MessagesConstants.ROOM_NAME_ALREADY_EXISTS;
import static com.example.catalog.util.MessagesConstants.ROOM_NAME_CAN_NOT_BE_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.room.response.RoomResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration tests for POST, PUT, DELETE operations on Room entity.
 */
public class ManageRoomIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private RoomRepository roomRepository;

  private final ObjectMapper mapper = new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectRoomCreate_whenCreateRoom_thenCorrect() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(RoomHelper.roomUrlPath)
        .content(mapper.writeValueAsString(RoomHelper.createRoomCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    RoomResponse roomResponse =
            mapper.readValue(response.getResponse().getContentAsString(), RoomResponse.class);

    assertEquals(RoomHelper.name, roomResponse.name());
    assertEquals(1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectRoomCreateExistingName_whenCreateRoom_thenException() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .post(RoomHelper.roomUrlPath)
        .content(mapper.writeValueAsString(RoomHelper.createRoomCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    var response = mockMvc.perform(MockMvcRequestBuilders
        .post(RoomHelper.roomUrlPath)
        .content(mapper.writeValueAsString(RoomHelper.createRoomCreate()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(ROOM_NAME_ALREADY_EXISTS));
    assertEquals(1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectRoomCreateBlankName_whenCreateRoom_thenException() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
                    .post(RoomHelper.roomUrlPath)
                    .content(mapper.writeValueAsString(RoomHelper.createEmptyRoomCreate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(ROOM_NAME_CAN_NOT_BE_BLANK));
    assertEquals(0, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectRoomUpdate_whenUpdateRoom_thenCorrect() throws Exception {
    Room room = roomRepository.save(RoomHelper.createRoom());

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(RoomHelper.roomUrlPath, room.getId()))
                    .content(mapper.writeValueAsString(RoomHelper.createRoomUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    RoomResponse roomResponse =
            mapper.readValue(response.getResponse().getContentAsString(), RoomResponse.class);

    assertEquals(room.getId(), roomResponse.id());
    assertEquals(RoomHelper.nameUpdated, roomResponse.name());
    assertEquals(RoomHelper.nameUpdated, roomRepository.findById(room.getId()).get().getName());
    assertEquals(1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectRoomUpdateBlankName_whenUpdateRoom_thenException() throws Exception {
    Room room = roomRepository.save(RoomHelper.createRoom());

    var response = mockMvc.perform(MockMvcRequestBuilders
        .put(createUrlPathWithId(RoomHelper.roomUrlPath, room.getId()))
        .content(mapper.writeValueAsString(RoomHelper.createRoomUpdateBlankName()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(ROOM_NAME_CAN_NOT_BE_BLANK));
    assertEquals(1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectRoomUpdateNameAlreadyExists_whenUpdateRoom_thenException()
          throws Exception {

    Room room = roomRepository.save(RoomHelper.createRoom());

    var response = mockMvc.perform(MockMvcRequestBuilders
        .put(createUrlPathWithId(RoomHelper.roomUrlPath, room.getId()))
        .content(mapper.writeValueAsString(RoomHelper.createRoomUpdateWithExistingName()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    System.out.println("test-- " + errorMessage);

    assertTrue(errorMessage.contains(ROOM_NAME_ALREADY_EXISTS));
    assertEquals(1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectRoomUpdateIdNotExists_whenUpdateRoom_thenException() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(RoomHelper.roomUrlPath, invalidId))
                    .content(mapper.writeValueAsString(RoomHelper.createRoomUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Room.class.getSimpleName(), invalidId), errorMessage);
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenDeleteRoom_thenCorrect() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));
    Room room = roomRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(RoomHelper.roomUrlPath, room.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(RoomHelper.testRoomsCount - 1, roomRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenDeleteRoom_thenException() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(RoomHelper.roomUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(createEntityNotExistsMessage(Room.class.getSimpleName(), invalidId), errorMessage);
    assertEquals(RoomHelper.testRoomsCount, roomRepository.findAll().size());
  }
}
