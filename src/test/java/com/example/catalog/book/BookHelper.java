package com.example.catalog.book;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BookHelper {
  public static final String bookUrlPath = "/books";
  public static final Long id = 1L;
  public static final String title = "Book Test";
  public static final String titleUpdated = "Book Test Updated";
  public static final int testBooksCount = 10;

  public static Book createBook() {

    return Book.builder()
            .id(id)
            .title(title)
            .statusType(StatusTypeHelper.createStatusType())
            .shelf(ShelfHelper.createShelf())
            .publisher(PublisherHelper.createPublisher())
            .author(AuthorHelper.createAuthor())
            .build();
  }

  public static BookCreate createBookCreate() {

    return new BookCreate(
            title,
            id,
            id,
            id,
            id
    );
  }

  public static BookResponse createBookResponse() {

    return new BookResponse(
            id,
            title,
            null,
            null,
            null,
            null
    );
  }

  public static BookUpdate createBookUpdate() {

    return new BookUpdate(
            titleUpdated,
            null,
            null,
            null,
            null
    );
  }

  public static List<Book> prepareBookList() {
    List<Book> list = new ArrayList<>();
    for (int i = 0; i < testBooksCount; i++) {
      list.add(Book.builder()
              .title(title + i)
              .build());
    }

    return list;
  }

  public static BookCreate createEmptyBookCreate() {
    
    return new BookCreate(
            "",
            id,
            id,
            id,
            id
    );
  }

  public static BookUpdate createBookUpdateBlankName() {

    return new BookUpdate(
          "",
          null,
          null,
          null,
          null
  );
  }

  public static BookCreate createBookCreateWithAllIds(
      Long authorId,
      Long publisherId,
      Long shelfId,
      Long statusTypeId
  ) {

    return new BookCreate(
            title,
            authorId,
            publisherId,
            shelfId,
            statusTypeId
    );
  }
}
