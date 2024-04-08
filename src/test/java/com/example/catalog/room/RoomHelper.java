package com.example.catalog.room;

import com.example.catalog.room.entity.Room;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for test with Room entity.
 */
public class RoomHelper {

  public static final String roomUrlPath = "/rooms";
  public static final Long id = 1L;
  public static final String name = "Room Test";
  public static final String nameUpdated = "Room Test Updated";
  public static final int testRoomsCount = 18;

  /**
   * Creates Room object.
   *
   * @return Room entity.
   */
  public static Room createRoom() {

    return Room.builder()
        .id(id)
        .name(name)
        .build();
  }

  /**
   * Creates List of Room objects.
   *
   * @return List of Room entities.
   */
  public static List<Room> prepareRoomList() {
    List<Room> list = new ArrayList<>();
    for (int i = 0; i < testRoomsCount; i++) {
      list.add(Room.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  /**
   * Creates RoomCreate record request.
   *
   * @return RoomCreate record.
   */
  public static RoomCreate createRoomCreate() {

    return new RoomCreate(
        name
    );
  }

  /**
   * Creates RoomResponse record.
   *
   * @return RoomResponse record.
   */
  public static RoomResponse createRoomResponse() {

    return new RoomResponse(
        id,
        name
    );
  }

  /**
   * Create RoomUpdate record request.
   *
   * @return RoomUpdate record.
   */
  public static RoomUpdate createRoomUpdate() {

    return new RoomUpdate(
            nameUpdated
    );
  }

  /**
   * Creates RoomCreate record request with blank name.
   *
   * @return RoomCreate record.
   */
  public static RoomCreate createEmptyRoomCreate() {

    return new RoomCreate(
            ""
    );
  }

  /**
   * Creates RoomUpdate record request with blank name.
   *
   * @return RoomUpdate record.
   */
  public static RoomUpdate createRoomUpdateBlankName() {

    return new RoomUpdate(
            ""
    );
  }

  /**
   * Create RoomUpdate record request with already existing name.
   *
   * @return RoomUpdate record.
   */
  public static RoomUpdate createRoomUpdateWithExistingName() {

    return new RoomUpdate(
            name
    );
  }

  /**
   * Creates RoomCreate record request with given name.
   *
   * @param roomNewName String name of the room.
   * @return RoomCreate record.
   */
  public static RoomCreate createRoomWithName(String roomNewName) {

    return new RoomCreate(
          roomNewName
    );
  }
}
