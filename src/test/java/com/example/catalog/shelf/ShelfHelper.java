package com.example.catalog.shelf;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;

import java.util.ArrayList;
import java.util.List;

public class ShelfHelper {

  public static final String shelfUrlPath = "/shelves";
  public static final Long id = 1L;
  public static final String letter = "A";
  public static final String letterUpdate = "B";
  public static final Integer number = 1;
  public static final Integer numberUpdate = 3;
  public static final int testShelvesCount = 10;

  public static Shelf createShelf() {

    return Shelf.builder()
            .id(id)
            .letter(letter)
            .number(number)
            .room(RoomHelper.createRoom())
            .build();
  }

  public static ShelfCreate createShelfCreate() {

    return new ShelfCreate(
            letter,
            number,
            RoomHelper.id
    );
  }

  public static ShelfUpdate createShelfUpdate() {

    return new ShelfUpdate(
            letter,
            numberUpdate,
            null
    );
  }

  public static ShelfResponse createShelfResponse() {

    return new ShelfResponse(
            id,
            letter,
            number,
            RoomHelper.createRoomResponse()
    );
  }

  public static List<Shelf> prepareShelfList() {
    List<Shelf> list = new ArrayList<>();
    for (int i = 0; i < testShelvesCount; i++) {
      list.add(Shelf.builder()
              .id((long) (i + 1))
              .number(i)
              .letter(Integer.toString(i))
              .build());
    }

    return list;
  }

  public static ShelfCreate createShelfCreateWithNoLetter() {

    return new ShelfCreate(
            null,
            number,
            RoomHelper.id
    );
  }

  public static ShelfCreate createShelfCreateWithNoNumber() {

    return new ShelfCreate(
            letter,
            null,
            RoomHelper.id
    );
  }

  public static ShelfCreate createShelfCreateWithNoRoomId() {

    return new ShelfCreate(
            letter,
            number,
            null
    );
  }

  public static ShelfCreate createShelfCreateWithRoomId(Long roomId) {

    return new ShelfCreate(
            letter,
            number,
            roomId
    );
  }

  public static Shelf createShelfAfterUpdate() {

    return Shelf.builder()
            .id(id)
            .letter(letter)
            .number(numberUpdate)
            .room(RoomHelper.createRoom())
            .build();
  }
}
