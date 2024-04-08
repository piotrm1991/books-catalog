package com.example.catalog.author;

import com.example.catalog.author.entity.Author;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for author tests.
 */
public class AuthorHelper {

  public static final String authorUrlPath = "/authors";
  public static final Long id = 1L;
  public static final String name = "Author Test";
  public static final String nameUpdated = "Author Test Updated";
  public static final int testAuthorsCount = 18;

  /**
   * Creates Author object.
   *
   * @return Author object.
   */
  public static Author createAuthor() {

    return Author.builder()
        .id(id)
        .name(name)
        .build();
  }

  /**
   * Creates list of Author objects.
   *
   * @return List of Authors.
   */
  public static List<Author> prepareAuthorList() {
    List<Author> list = new ArrayList<>();
    for (int i = 0; i < testAuthorsCount; i++) {
      list.add(Author.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  /**
   * Creates AuthorCreate record request.
   *
   * @return AuthorCreate record.
   */
  public static AuthorCreate createAuthorCreate() {

    return new AuthorCreate(
        name
    );
  }

  /**
   * Creates AuthorResponse record.
   *
   * @return AuthorResponse record.
   */
  public static AuthorResponse createAuthorResponse() {

    return new AuthorResponse(
        id,
        name
    );
  }

  /**
   * Creates AuthorUpdate record request.
   *
   * @return AuthorUpdate record.
   */
  public static AuthorUpdate createAuthorUpdate() {

    return new AuthorUpdate(
            nameUpdated
    );
  }

  /**
   * Creates AuthorCreate record request with empty author name.
   *
   * @return AuthorCreate record.
   */
  public static AuthorCreate createEmptyAuthorCreate() {

    return new AuthorCreate(
            ""
    );
  }

  /**
   * Creates AuthorUpdate record request with empty author name.
   *
   * @return AuthorUpdate record.
   */
  public static AuthorUpdate createAuthorUpdateBlankName() {

    return new AuthorUpdate(
            ""
    );
  }


  /**
   * Creates AuthorUpdate record request with already created name.
   *
   * @return AuthorUpdate record.
   */
  public static AuthorUpdate createAuthorUpdateWithExistingName() {

    return new AuthorUpdate(
            name
    );
  }
}
