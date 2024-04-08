package com.example.catalog.integration.room;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.mapper.RoomMapper;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.room.response.RoomResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.RestPageImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.catalog.util.MessagesConstants.createEntityNotExistsMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ViewRoomIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private RoomMapper roomMapper;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenGetRoomById_theReturnRoomResponseCorrect() throws Exception {
    Room expectedRoom = RoomHelper.createRoom();
    expectedRoom = roomRepository.save(expectedRoom);

    var response = mockMvc.perform(get(createUrlPathWithId(RoomHelper.roomUrlPath, expectedRoom.getId()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    RoomResponse roomResponse = mapper.readValue(response.getResponse().getContentAsString(), RoomResponse.class);

    assertEquals(expectedRoom.getId(), roomResponse.id());
    assertEquals(expectedRoom.getName(), roomResponse.name());

    assertThat(roomRepository.getReferenceById(expectedRoom.getId())).isEqualTo(expectedRoom);
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenGetById_thenException() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(get(createUrlPathWithId(RoomHelper.roomUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();
    assertTrue(errorMessage.contains(createEntityNotExistsMessage(Room.class.getSimpleName(), invalidId)));

  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllRooms_defaultPageRequest() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));

    var response = mockMvc.perform(get(RoomHelper.roomUrlPath)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<RoomResponse> roomsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<RoomResponse>>() {});

    assertFalse(roomsResponse.isEmpty());
    assertEquals( RoomHelper.testRoomsCount, roomsResponse.getTotalElements());
    assertEquals(4, roomsResponse.getTotalPages());
    assertEquals(5, roomsResponse.getContent().size());

    for (int i = 0; i < 5; i++) {
      AssertionsForClassTypes.assertThat(roomsResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(roomMapper.mapEntityToResponse(roomList.get(i)));
    }
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllRooms_empty() throws Exception {
    var response = mockMvc.perform(get(RoomHelper.roomUrlPath)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<RoomResponse> roomsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<RoomResponse>>() {});

    assertTrue(roomsResponse.isEmpty());
    assertEquals( 0, roomsResponse.getTotalElements());
    assertEquals(0, roomsResponse.getTotalPages());
    assertEquals(0, roomsResponse.getContent().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void testGetAllRooms_customPageRequest() throws Exception {
    List<Room> roomList = RoomHelper.prepareRoomList();
    roomList.forEach(a -> roomRepository.save(a));
    Page<Room> expectedRoomList = roomRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

    var response = mockMvc.perform(get(createUrlPathGetPageable(RoomHelper.roomUrlPath, 1, 3, "id", false))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']").value("true"))
            .andReturn();
    Page<RoomResponse> roomsResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<RoomResponse>>() {});

    assertFalse(roomsResponse.isEmpty());
    assertEquals(expectedRoomList.getTotalElements(), roomsResponse.getTotalElements());
    assertEquals(expectedRoomList.getTotalPages(), roomsResponse.getTotalPages());
    assertEquals(expectedRoomList.getContent().size(), roomsResponse.getContent().size());

    for (int i = 0; i < expectedRoomList.getContent().size(); i++) {
      AssertionsForClassTypes.assertThat(roomsResponse.getContent().get(i))
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(roomMapper.mapEntityToResponse(expectedRoomList.getContent().get(i)));
    }
  }
}
