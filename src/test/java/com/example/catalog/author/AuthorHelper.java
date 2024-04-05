package com.example.catalog.author;

import com.example.catalog.author.entity.Author;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import java.util.ArrayList;
import java.util.List;

public class AuthorHelper {

  public static final String authorUrlPath = "/authors";
  public static final Long id = 1L;
  public static final String name = "Author Test";
  public static final String nameUpdated = "Author Test Updated";
  public static final int testAuthorsCount = 18;

  public static Author createAuthor() {

    return Author.builder()
        .id(id)
        .name(name)
        .build();
  }

  public static List<Author> prepareAuthorList() {
    List<Author> list = new ArrayList<>();
    for (int i = 0; i < testAuthorsCount; i++) {
      list.add(Author.builder()
              .name(name + i)
              .build());
    }

    return list;
  }

  public static AuthorCreate createAuthorCreate() {

    return new AuthorCreate(
        name
    );
  }

  public static AuthorResponse createAuthorResponse() {

    return new AuthorResponse(
        id,
        name
    );
  }

  public static AuthorUpdate createAuthorUpdate() {

    return new AuthorUpdate(
            nameUpdated
    );
  }

  public static AuthorCreate createEmptyAuthorCreate() {

    return new AuthorCreate(
            ""
    );
  }

  public static AuthorUpdate createAuthorUpdateBlankName() {

    return new AuthorUpdate(
            ""
    );
  }

  public static AuthorUpdate createAuthorUpdateWithExistingName() {

    return new AuthorUpdate(
            name
    );
  }
}
