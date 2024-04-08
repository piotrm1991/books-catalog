package com.example.catalog.book;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.publisher.PublisherHelper;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.shelf.ShelfHelper;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.statustype.StatusTypeHelper;
import com.example.catalog.statustype.entity.StatusType;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for test with Book entity.
 */
public class BookHelper {
  public static final String bookUrlPath = "/books";
  public static final Long id = 1L;
  public static final String title = "Book Test";
  public static final String titleUpdated = "Book Test Updated";
  public static final int testBooksCount = 10;

  /**
   * Create Book object.
   *
   * @return Book entity.
   */
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

  /**
   * Creates BookCreate record request.
   *
   * @return BookCreate record.
   */
  public static BookCreate createBookCreate() {

    return new BookCreate(
            title,
            id,
            id,
            id,
            id
    );
  }

  /**
   * Create BookResponse record.
   *
   * @return BookResponse record.
   */
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

  /**
   * Creates BookUpdate record request.
   *
   * @return BookUpdate record.
   */
  public static BookUpdate createBookUpdate() {

    return new BookUpdate(
            titleUpdated,
            null,
            null,
            null,
            null
    );
  }

  /**
   * Creates list of Book objects for tests.
   *
   * @return List of Book objects.
   */
  public static List<Book> prepareBookList() {
    List<Book> list = new ArrayList<>();
    for (int i = 0; i < testBooksCount; i++) {
      list.add(Book.builder()
              .title(title + i)
              .build());
    }

    return list;
  }

  /**
   * Creates BookCreate record with blank book title.
   *
   * @return BookCreate record.
   */
  public static BookCreate createEmptyBookCreate() {
    
    return new BookCreate(
            "",
            id,
            id,
            id,
            id
    );
  }

  /**
   * Creates BookUpdate record with blank title and null ids.
   *
   * @return BookUpdate record.
   */
  public static BookUpdate createBookUpdateBlankName() {

    return new BookUpdate(
          "",
          null,
          null,
          null,
          null
  );
  }

  /**
   * Create BookCreate record with give ids.
   *
   * @param authorId Long Author id.
   * @param publisherId Long Publisher id.
   * @param shelfId Long Shelf id.
   * @param statusTypeId Long StatusType id.
   * @return BookCreate record.
   */
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

  /**
   * Create Book object with given author, publisher, shelf and statusType.
   *
   * @param author Author entity.
   * @param publisher Publisher entity.
   * @param shelf Shelf entity.
   * @param statusType StatusType entity.
   * @return Book object.
   */
  public static Book createBookWithGivenAllSubEntities(
        Author author,
        Publisher publisher,
        Shelf shelf,
        StatusType statusType
  ) {

    return Book.builder()
          .id(id)
          .title(title)
          .statusType(statusType)
          .shelf(shelf)
          .publisher(publisher)
          .author(author)
          .build();
  }
}
