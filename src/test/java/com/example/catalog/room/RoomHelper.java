package com.example.catalog.room;

import com.example.catalog.room.entity.Room;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;

import java.util.ArrayList;
import java.util.List;

public class RoomHelper {

  public static final String roomUrlPath = "/rooms";
  public static final Long id = 1L;
  public static final String name = "Room Test";
  public static final String nameUpdated = "Room Test Updated";
  public static final int testRoomsCount = 18;

  public static Room createRoom() {

    return Room.builder()
        .id(id)
        .name(name)
        .build();
  }

  public static List<Room> prepareRoomList() {
    List<Room> list = new ArrayList<>();
    for (int i = 0; i < testRoomsCount; i++) {
      list.add(Room.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  public static RoomCreate createRoomCreate() {

    return new RoomCreate(
        name
    );
  }

  public static RoomResponse createRoomResponse() {

    return new RoomResponse(
        id,
        name
    );
  }

  public static RoomUpdate createRoomUpdate() {

    return new RoomUpdate(
            nameUpdated
    );
  }

  public static RoomCreate createEmptyRoomCreate() {

    return new RoomCreate(
            ""
    );
  }

  public static RoomUpdate createRoomUpdateBlankName() {

    return new RoomUpdate(
            ""
    );
  }

  public static RoomUpdate createRoomUpdateWithExistingName() {

    return new RoomUpdate(
            name
    );
  }

  public static RoomCreate createRoomWithName(String roomNewName) {

    return new RoomCreate(
          roomNewName
    );
  }
}
