package com.example.catalog.publisher;

import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Helpers class for test with Publisher entity.
 */
public class PublisherHelper {

  public static final String publisherUrlPath = "/publishers";
  public static final Long id = 1L;
  public static final String name = "Publisher Test";
  public static final String nameUpdated = "Publisher Test Updated";
  public static final int testPublishersCount = 18;

  /**
   * Creates Publisher object.
   *
   * @return Publisher entity.
   */
  public static Publisher createPublisher() {

    return Publisher.builder()
        .id(id)
        .name(name)
        .build();
  }

  /**
   * Creates List of Publisher objets.
   *
   * @return List of Publisher entities.
   */
  public static List<Publisher> preparePublisherList() {
    List<Publisher> list = new ArrayList<>();
    for (int i = 0; i < testPublishersCount; i++) {
      list.add(Publisher.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  /**
   * Create PublisherCreate record request.
   *
   * @return PublisherCreate record.
   */
  public static PublisherCreate createPublisherCreate() {

    return new PublisherCreate(
        name
    );
  }

  /**
   * Create PublisherResponse record.
   *
   * @return PublisherResponse record.
   */
  public static PublisherResponse createPublisherResponse() {

    return new PublisherResponse(
        id,
        name
    );
  }

  /**
   * Creates PublisherUpdate record request.
   *
   * @return PublisherUpdate record.
   */
  public static PublisherUpdate createPublisherUpdate() {

    return new PublisherUpdate(
            nameUpdated
    );
  }

  /**
   * Creates PublisherCreate record request with blank publisher name.
   *
   * @return PublisherCreate record.
   */
  public static PublisherCreate createEmptyPublisherCreate() {

    return new PublisherCreate(
            ""
    );
  }

  /**
   * Creates PublisherUpdate record request with blank publisher name.
   *
   * @return PublisherUpdate record.
   */
  public static PublisherUpdate createPublisherUpdateBlankName() {

    return new PublisherUpdate(
            ""
    );
  }

  /**
   * Creates PublisherUpdate record request with already existing name.
   *
   * @return PublisherUpdate record.
   */
  public static PublisherUpdate createPublisherUpdateWithExistingName() {

    return new PublisherUpdate(
            name
    );
  }
}
