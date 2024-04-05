package com.example.catalog.author.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.catalog.author.AuthorHelper;
import com.example.catalog.author.entity.Author;
import com.example.catalog.author.mapper.AuthorMapper;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.author.service.impl.AuthorServiceImpl;
import com.example.catalog.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

  @InjectMocks
  private AuthorServiceImpl authorService;

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private AuthorMapper authorMapper;

  private Author author;
  private AuthorCreate authorCreate;
  private AuthorUpdate authorUpdate;
  private AuthorResponse authorResponse;

  @BeforeEach
  void setUp() {
    author = AuthorHelper.createAuthor();
    authorCreate = AuthorHelper.createAuthorCreate();
    authorUpdate = AuthorHelper.createAuthorUpdate();
    authorResponse = AuthorHelper.createAuthorResponse();
  }

  @Test
  void givenCorrectAuthorRequest_whenCreateAuthor_thenCorrect() {
    when(authorMapper.mapAuthorCreateToEntity(authorCreate)).thenReturn(author);
    when(authorMapper.mapEntityToResponse(author)).thenReturn(authorResponse);
    when(authorRepository.save(any(Author.class))).thenReturn(author);

    AuthorResponse actualAuthorResponse = authorService.createAuthor(authorCreate);

    assertEquals(authorResponse, actualAuthorResponse);
    verify(authorRepository, times(1)).save(author);
  }

  @Test
  void givenCorrectAuthorRequest_whenUpdateAuthor_thenCorrect() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
    when(authorRepository.save(any(Author.class))).thenReturn(author);
    when(authorMapper.mapEntityToResponse(any(Author.class))).thenReturn(authorResponse);

    AuthorResponse actualAuthorResponse = authorService.updateAuthor(AuthorHelper.id, authorUpdate);

    assertEquals(authorResponse, actualAuthorResponse);
    verify(authorRepository, times(1)).save(author);
  }

  @Test
  void givenCorrectId_whenGetAuthorById_thenCorrect() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

    Author actualAuthor = authorService.getAuthorById(anyLong());

    assertEquals(author, actualAuthor);
  }

  @Test
  void givenIncorrectId_whenGetAuthorById_thenException() {
    Long invalidId = 1L;

    when(authorRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> authorService.getAuthorById(invalidId));
    String expectedMessage = "Author with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void givenCorrectId_whenDeleteAuthorById_thenCorrect() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

    authorService.deleteAuthorById(anyLong());

    verify(authorRepository, times(1)).delete(author);
  }

  @Test
  void givenIncorrectId_whenDeleteAuthorById_thenException() {
    Long invalidId = 1L;

    when(authorRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> authorService.deleteAuthorById(invalidId));
    String expectedMessage = "Author with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenGetAllAuthorsPage_thenCorrect() {
    Page expectedPageSize = new PageImpl<>(List.of());
    when(authorRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedPageSize);

    Page actualPageSize = authorService.getAllAuthorsPage(PageRequest.of(0, 10));

    assertEquals(expectedPageSize, actualPageSize);
  }

  @Test
  void givenCorrectId_whenGetAuthorResponseById_thenCorrect() {
    when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
    when(authorMapper.mapEntityToResponse(author)).thenReturn(authorResponse);

    AuthorResponse actualAuthorResponse = authorService.getAuthorResponseById(anyLong());

    assertEquals(authorResponse, actualAuthorResponse);
  }

  @Test
  void givenIncorrectId_whenGetAuthorResponseById_thenException() {
    Long invalidId = 1L;

    when(authorRepository.findById(invalidId)).thenReturn(Optional.empty());
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> authorService.getAuthorResponseById(invalidId));
    String expectedMessage = "Author with id: " + invalidId + " is not found.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}