package com.example.catalog.shelf.mapper;

import com.example.catalog.room.service.RoomService;
import com.example.catalog.room.service.impl.RoomServiceImpl;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShelfMapperTest {

  @InjectMocks
  private ShelfMapper shelfMapper;

  @Mock
  private ObjectMapper mapper;
  @Mock
  private RoomServiceImpl roomService;

  private Shelf shelf;
  private ShelfCreate shelfCreate;
  private ShelfUpdate shelfUpdate;
  private ShelfResponse shelfResponse;
  private Shelf shelfAfterUpdate;

  @BeforeEach
  void setUp() {
    shelf = ShelfHelper.createShelf();
    shelfCreate = ShelfHelper.createShelfCreate();
    shelfUpdate = ShelfHelper.createShelfUpdate();
    shelfResponse = ShelfHelper.createShelfResponse();
    shelfAfterUpdate = ShelfHelper.createShelfAfterUpdate();
  }

  @Test
  void givenCorrectRequest_whenMapRequestToEntity_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(shelfCreate), Shelf.class)).thenReturn(shelf);

    Shelf expectedShelf = shelfMapper.mapShelfCreateToEntity(shelfCreate);

    assertEquals(expectedShelf, shelf);
  }

  @Test
  void givenCorrectEntity_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(shelf), ShelfResponse.class)).thenReturn(shelfResponse);

    ShelfResponse expectedShelfResponse = shelfMapper.mapEntityToResponse(shelf);

    assertEquals(expectedShelfResponse, shelfResponse);
  }

  @Test
  void givenCorrectUpdateRequest_whenMapShelfUpdateToEntity_thenCorrect() throws JsonProcessingException {
    Shelf expectedShelfUpdate = shelfMapper.mapShelfUpdateToEntity(shelf, shelfUpdate);

    assertEquals(expectedShelfUpdate.getNumber(), shelfAfterUpdate.getNumber());
    assertEquals(expectedShelfUpdate.getLetter(), shelfAfterUpdate.getLetter());
    assertEquals(expectedShelfUpdate.getId(), shelfAfterUpdate.getId());
  }
}
