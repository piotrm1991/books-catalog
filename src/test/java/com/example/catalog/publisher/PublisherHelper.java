package com.example.catalog.publisher;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import java.util.ArrayList;
import java.util.List;

public class PublisherHelper {

  public static final String publisherUrlPath = "/publishers";
  public static final Long id = 1L;
  public static final String name = "Publisher Test";
  public static final String nameUpdated = "Publisher Test Updated";
  public static final int testPublishersCount = 18;

  public static Publisher createPublisher() {

    return Publisher.builder()
        .id(id)
        .name(name)
        .build();
  }

  public static List<Publisher> preparePublisherList() {
    List<Publisher> list = new ArrayList<>();
    for (int i = 0; i < testPublishersCount; i++) {
      list.add(Publisher.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  public static PublisherCreate createPublisherCreate() {

    return new PublisherCreate(
        name
    );
  }

  public static PublisherResponse createPublisherResponse() {

    return new PublisherResponse(
        id,
        name
    );
  }

  public static PublisherUpdate createPublisherUpdate() {

    return new PublisherUpdate(
            nameUpdated
    );
  }

  public static PublisherCreate createEmptyPublisherCreate() {

    return new PublisherCreate(
            ""
    );
  }

  public static PublisherUpdate createPublisherUpdateBlankName() {

    return new PublisherUpdate(
            ""
    );
  }

  public static PublisherUpdate createPublisherUpdateWithExistingName() {

    return new PublisherUpdate(
            name
    );
  }
}
