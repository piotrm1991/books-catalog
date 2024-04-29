package com.example.catalog.statustype;

import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for tests with StatusType entity.
 */
public class StatusTypeHelper {

  public static final String statusTypeUrlPath = "/statusTypes";
  public static final Long id = 1L;
  public static final String name = "StatusType Test";
  public static final String nameUpdated = "StatusType Test Updated";
  public static final int testStatusTypesCount = 10;
  public static final int page = 0;
  public static final int size = 5;

  /**
   * Creates StatusType object.
   *
   * @return StatusType entity.
   */
  public static StatusType createStatusType() {

    return StatusType.builder()
        .id(id)
        .name(name)
        .build();
  }

  /**
   * Creates List of StatusType objects.
   *
   * @return List of StatusType entities.
   */
  public static List<StatusType> prepareStatusTypeList() {
    List<StatusType> list = new ArrayList<>();
    for (int i = 0; i < testStatusTypesCount; i++) {
      list.add(StatusType.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  /**
   * Create StatusTypeCreate record request.
   *
   * @return StatusTypeCreate record.
   */
  public static StatusTypeCreate createStatusTypeCreate() {

    return new StatusTypeCreate(
        name
    );
  }

  /**
   * Create StatusTypeResponse record.
   *
   * @return StatusTypeResponse record.
   */
  public static StatusTypeResponse createStatusTypeResponse() {

    return new StatusTypeResponse(
        id,
        name
    );
  }

  /**
   * Create StatusTypeUpdate record.
   *
   * @return StatusTypeUpdate record.
   */
  public static StatusTypeUpdate createStatusTypeUpdate() {

    return new StatusTypeUpdate(
            nameUpdated
    );
  }

  /**
   * Create StatusTypeCreate record request.
   *
   * @return StatusTypeCreate record.
   */
  public static StatusTypeCreate createEmptyStatusTypeCreate() {

    return new StatusTypeCreate(
            ""
    );
  }

  /**
   * Create StatusTypeUpdate record request with blank name.
   *
   * @return StatusTypeUpdate record.
   */
  public static StatusTypeUpdate createStatusTypeUpdateBlankName() {

    return new StatusTypeUpdate(
            ""
    );
  }

  /**
   * Create StatusTypeUpdate record request with already existing name.
   *
   * @return StatusTypeUpdate record.
   */
  public static StatusTypeUpdate createStatusTypeUpdateWithExistingName() {

    return new StatusTypeUpdate(
            name
    );
  }

  /**
   * Create StatusTypeCreate record request with given name.
   *
   * @return StatusTypeCreate record.
   */
  public static StatusTypeCreate createStatusTypeWithGivenName(String statusTypeNewName) {

    return new StatusTypeCreate(
          statusTypeNewName
    );
  }

  public static String getStatusTypeUrlPathWithPageAndSize(int page, int size) {

    return statusTypeUrlPath + "?page=" + page + "&size=" + size;
  }

  public static String getStatusTypeUrlPathWithPageAndSize() {

    return statusTypeUrlPath + "?page=" + page + "&size=" + size;
  }
}
