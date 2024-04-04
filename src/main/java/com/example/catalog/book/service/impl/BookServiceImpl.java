package com.example.catalog.book.service.impl;

import static com.example.catalog.util.ErrorMessagesConstants.createBookNotExistMessage;

import com.example.catalog.author.service.AuthorService;
import com.example.catalog.book.entity.Book;
import com.example.catalog.book.mapper.BookMapper;
import com.example.catalog.book.repository.BookRepository;
import com.example.catalog.book.request.BookCreate;
import com.example.catalog.book.request.BookUpdate;
import com.example.catalog.book.response.BookResponse;
import com.example.catalog.book.service.BookService;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.publisher.service.PublisherService;
import com.example.catalog.shelf.service.ShelfService;
import com.example.catalog.statustype.service.StatusTypeService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the BookService interface for managing book requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final AuthorService authorService;
  private final PublisherService publisherService;
  private final ShelfService shelfService;
  private final StatusTypeService statusTypeService;

  @Override
  public BookResponse createBook(BookCreate bookCreate) {
    log.info("Creating book with title: {}", bookCreate.title());
    Book book = bookMapper.mapBookCreateToEntity(bookCreate);
    book.setAuthor(authorService.getAuthorById(bookCreate.authorId()));
    if (bookCreate.publisherId() != null) {
      book.setPublisher(publisherService.getPublisherById(bookCreate.publisherId()));
    }
    if (bookCreate.shelfId() != null) {
      book.setShelf(shelfService.getShelfById(bookCreate.shelfId()));
    }
    book.setStatusType(statusTypeService.getStatusTypeById(bookCreate.shelfId()));
    book = save(book);

    return bookMapper.mapEntityToResponse(book);
  }

  @Override
  public BookResponse updateBook(Long id, BookUpdate bookUpdate) {
    log.info("Updating book with id: {}", id);
    Book bookToUpdate = bookMapper.mapBookUpdateToEntity(getBookById(id), bookUpdate);
    if (bookUpdate.authorId() != null) {
      bookToUpdate.setAuthor(authorService.getAuthorById(bookUpdate.authorId()));
    }
    if (bookUpdate.shelfId() != null) {
      bookToUpdate.setShelf(shelfService.getShelfById(bookUpdate.shelfId()));
    }
    if (bookUpdate.publisherId() != null) {
      bookToUpdate.setPublisher(publisherService.getPublisherById(bookUpdate.publisherId()));
    }
    if (bookUpdate.statusTypeId() != null) {
      bookToUpdate.setStatusType(statusTypeService.getStatusTypeById(bookUpdate.statusTypeId()));
    }
    bookToUpdate = save(bookToUpdate);

    return bookMapper.mapEntityToResponse(bookToUpdate);
  }

  @Override
  public BookResponse getBookResponseById(Long id) {
    log.info("Getting book with id: {}", id);

    return bookMapper.mapEntityToResponse(getBookById(id));
  }

  @Override
  public Page<BookResponse> getAllBooksPage(Pageable pageable) {
    log.info("Getting all books from database.");

    return bookRepository.findAll(pageable).map(bookMapper::mapEntityToResponse);
  }

  @Override
  public void deleteBookById(Long id) {
    log.info("Deleting book with id: {}", id);
    delete(getBookById(id));
  }

  @Override
  public Book getBookById(Long id) {
    log.info("Getting book from database with id: {}", id);

    return bookRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(createBookNotExistMessage(id)));
  }

  @Transactional
  private void delete(Book book) {
    log.info("Deleting book with id: {} from database.", book.getId());
    bookRepository.delete(book);
  }

  @Transactional
  private Book save(Book book) {
    log.info("Saving book with title: {} in database.", book.getTitle());

    return bookRepository.save(book);
  }
}
