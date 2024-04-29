package com.example.catalog.integration.shelf;

import static com.example.catalog.util.ExceptionMessagesConstants.createEntityNotExistsMessage;
import static com.example.catalog.util.MessagesConstants.SHELF_LETTER_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.SHELF_NUMBER_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.SHELF_ROOM_CAN_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.shelf.response.ShelfResponse;
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
 * Integration tests for POST, PUT, DELETE operations on Shelf entity.
 */
public class ManageShelvesIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private ShelfRepository shelfRepository;

  @Autowired
  private RoomRepository roomRepository;

  private final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectShelfCreate_whenCreateShelf_thenCorrect() throws Exception {
    Room room = RoomHelper.createRoom();
    room = roomRepository.save(room);

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(ShelfHelper.shelfUrlPath)
            .content(mapper.writeValueAsString(
                  ShelfHelper.createShelfCreateWithGivenRoomId(room.getId()))
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    ShelfResponse shelfResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          ShelfResponse.class
    );

    assertEquals(ShelfHelper.letter, shelfResponse.letter());
    assertEquals(ShelfHelper.number, shelfResponse.number());
    assertEquals(1, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectShelfCreateRoomNotExists_whenCreateShelf_thenException()
        throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .post(ShelfHelper.shelfUrlPath)
                    .content(mapper.writeValueAsString(ShelfHelper.createShelfCreate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage
          .contains(
                createEntityNotExistsMessage(Room.class.getSimpleName(),
                1L)
          ));
    assertEquals(0, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectShelfCreateBlankLetter_whenCreateShelf_thenException()
        throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .post(ShelfHelper.shelfUrlPath)
                    .content(mapper.writeValueAsString(ShelfHelper.createShelfCreateWithNoLetter()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(SHELF_LETTER_CAN_NOT_BE_BLANK));
    assertEquals(0, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectShelfCreateBlankNumber_whenCreateShelf_thenException()
        throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(ShelfHelper.shelfUrlPath)
            .content(mapper.writeValueAsString(ShelfHelper.createShelfCreateWithNoNumber()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(SHELF_NUMBER_CAN_NOT_BE_BLANK));
    assertEquals(0, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectShelfCreateBlankRoomId_whenCreateShelf_thenException()
        throws Exception {

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(ShelfHelper.shelfUrlPath)
            .content(mapper.writeValueAsString(ShelfHelper.createShelfCreateWithNoRoomId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(SHELF_ROOM_CAN_NOT_BE_NULL));
    assertEquals(0, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectShelfUpdate_whenUpdateShelf_thenCorrect() throws Exception {
    Room room = roomRepository.save(RoomHelper.createRoom());
    Shelf shelf = ShelfHelper.createShelf();
    shelf.setRoom(room);
    shelf = shelfRepository.save(shelf);

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(ShelfHelper.shelfUrlPath, shelf.getId()))
                    .content(mapper.writeValueAsString(ShelfHelper.createShelfUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    ShelfResponse shelfResponse = mapper.readValue(
          response.getResponse().getContentAsString(),
          ShelfResponse.class
    );

    assertEquals(shelf.getId(), shelfResponse.id());
    assertEquals(ShelfHelper.numberUpdate, shelfResponse.number());
    assertEquals(
          ShelfHelper.numberUpdate,
          shelfRepository.findById(shelf.getId()).get().getNumber()
    );
    assertEquals(1, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectShelfUpdateIdNotExists_whenUpdateShelf_thenException()
        throws Exception {

    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .put(createUrlPathWithId(ShelfHelper.shelfUrlPath, invalidId))
                    .content(mapper.writeValueAsString(ShelfHelper.createShelfUpdate()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
          createEntityNotExistsMessage(Shelf.class.getSimpleName(), invalidId),
          errorMessage
    );
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenDeleteShelf_thenCorrect() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));
    Shelf shelf = shelfRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(ShelfHelper.shelfUrlPath, shelf.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    assertEquals(ShelfHelper.testShelvesCount - 1, shelfRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenDeleteShelf_thenException() throws Exception {
    List<Shelf> shelfList = ShelfHelper.prepareShelfList();
    shelfList.forEach(a -> shelfRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
                    .delete(createUrlPathWithId(ShelfHelper.shelfUrlPath, invalidId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertEquals(
          createEntityNotExistsMessage(Shelf.class.getSimpleName(), invalidId),
          errorMessage
    );
    assertEquals(ShelfHelper.testShelvesCount, shelfRepository.findAll().size());
  }
}
