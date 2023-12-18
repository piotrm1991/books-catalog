package com.example.catalog.statustype;

import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;

import java.util.ArrayList;
import java.util.List;

public class StatusTypeHelper {

  public static final String statusTypeUrlPath = "/statusTypes";
  public static final Long id = 1L;
  public static final String name = "StatusType Test";
  public static final String nameUpdated = "StatusType Test Updated";
  public static final int testStatusTypesCount = 18;

  public static StatusType createStatusType() {

    return StatusType.builder()
        .id(id)
        .name(name)
        .build();
  }

  public static List<StatusType> prepareStatusTypeList() {
    List<StatusType> list = new ArrayList<>();
    for (int i = 0; i < testStatusTypesCount; i++) {
      list.add(StatusType.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  public static StatusTypeCreate createStatusTypeCreate() {

    return new StatusTypeCreate(
        name
    );
  }

  public static StatusTypeResponse createStatusTypeResponse() {

    return new StatusTypeResponse(
        id,
        name
    );
  }

  public static StatusTypeUpdate createStatusTypeUpdate() {

    return new StatusTypeUpdate(
            nameUpdated
    );
  }

  public static StatusTypeCreate createEmptyStatusTypeCreate() {

    return new StatusTypeCreate(
            ""
    );
  }

  public static StatusTypeUpdate createStatusTypeUpdateBlankName() {

    return new StatusTypeUpdate(
            ""
    );
  }

  public static StatusTypeUpdate createStatusTypeUpdateWithExistingName() {

    return new StatusTypeUpdate(
            name
    );
  }
}
