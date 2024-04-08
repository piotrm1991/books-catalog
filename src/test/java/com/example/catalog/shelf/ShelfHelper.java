package com.example.catalog.shelf;

import com.example.catalog.room.RoomHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Helpers class for tests with Shelf entity.
 */
public class ShelfHelper {

  public static final String shelfUrlPath = "/shelves";
  public static final Long id = 1L;
  public static final String letter = "A";
  public static final String letterUpdate = "B";
  public static final Integer number = 1;
  public static final Integer numberUpdate = 3;
  public static final int testShelvesCount = 10;

  /**
   * Create Shelf object.
   *
   * @return Shelf entity.
   */
  public static Shelf createShelf() {

    return Shelf.builder()
            .id(id)
            .letter(letter)
            .number(number)
            .room(RoomHelper.createRoom())
            .build();
  }

  /**
   * Create ShelfCreate record request.
   *
   * @return ShelfCreate record.
   */
  public static ShelfCreate createShelfCreate() {

    return new ShelfCreate(
            letter,
            number,
            RoomHelper.id
    );
  }

  /**
   * Creates ShelfUpdate record request.
   *
   * @return ShelfUpdate record.
   */
  public static ShelfUpdate createShelfUpdate() {

    return new ShelfUpdate(
            letter,
            numberUpdate,
            null
    );
  }

  /**
   * Create ShelfResponse record.
   *
   * @return ShelfResponse record.
   */
  public static ShelfResponse createShelfResponse() {

    return new ShelfResponse(
            id,
            letter,
            number,
            RoomHelper.createRoomResponse()
    );
  }

  /**
   * Create List of Shelf objects.
   *
   * @return List of Shelf entities.
   */
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

  /**
   * Creates ShelfCreate record request with blank letter.
   *
   * @return ShelfCreate record.
   */
  public static ShelfCreate createShelfCreateWithNoLetter() {

    return new ShelfCreate(
            null,
            number,
            RoomHelper.id
    );
  }

  /**
   * Creates ShelfCreate record request with blank number.
   *
   * @return ShelfCreate record.
   */
  public static ShelfCreate createShelfCreateWithNoNumber() {

    return new ShelfCreate(
            letter,
            null,
            RoomHelper.id
    );
  }

  /**
   * Creates ShelfCreate record request with blank room id.
   *
   * @return ShelfCreate record.
   */
  public static ShelfCreate createShelfCreateWithNoRoomId() {

    return new ShelfCreate(
            letter,
            number,
            null
    );
  }

  /**
   * Creates ShelfCreate record request with given room id.
   *
   * @return ShelfCreate record.
   */
  public static ShelfCreate createShelfCreateWithGivenRoomId(Long roomId) {

    return new ShelfCreate(
            letter,
            number,
            roomId
    );
  }


  /**
   * Creates Shelf object with updated data.
   *
   * @return Shelf entity.
   */
  public static Shelf createShelfAfterUpdate() {

    return Shelf.builder()
            .id(id)
            .letter(letter)
            .number(numberUpdate)
            .room(RoomHelper.createRoom())
            .build();
  }
}
