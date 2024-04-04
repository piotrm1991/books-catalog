package com.example.catalog.author.service.impl;

import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;

import com.example.catalog.author.entity.Author;
import com.example.catalog.author.mapper.AuthorMapper;
import com.example.catalog.author.repository.AuthorRepository;
import com.example.catalog.author.request.AuthorCreate;
import com.example.catalog.author.request.AuthorUpdate;
import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.author.service.AuthorService;
import com.example.catalog.exception.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthorService interface for managing author requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  @Override
  public AuthorResponse createAuthor(AuthorCreate authorCreate) {
    log.info("Creating author with name: {}", authorCreate.name());
    Author author = authorMapper.mapAuthorCreateToEntity(authorCreate);
    author = save(author);

    return authorMapper.mapEntityToResponse(author);
  }

  @Override
  public AuthorResponse updateAuthor(Long id, AuthorUpdate authorUpdate) {
    log.info("Updating author with id: {}", id);
    Author authorToUpdate = getAuthorById(id);
    authorToUpdate.setName(authorUpdate.name());
    authorToUpdate = save(authorToUpdate);

    return authorMapper.mapEntityToResponse(authorToUpdate);
  }

  @Override
  public AuthorResponse getAuthorResponseById(Long id) {
    log.info("Getting author with id: {}", id);

    return authorMapper.mapEntityToResponse(getAuthorById(id));
  }

  @Override
  public Page<AuthorResponse> getAllAuthorsPage(Pageable pageable) {
    log.info("Getting all authors from database.");

    return authorRepository.findAll(pageable).map(authorMapper::mapEntityToResponse);
  }

  //TODO: Add check if there are books added to this author
  @Override
  public void deleteAuthorById(Long id) {
    log.info("Deleting author with id: {}", id);
    delete(getAuthorById(id));
  }

  @Override
  public Author getAuthorById(Long id) {
    log.info("Getting author from database with id: {}", id);

    return authorRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(
                    createEntityNotExistsMessage(Author.class.getSimpleName(), id)));
  }

  @Override
  public boolean checkOfNameAlreadyExists(String name) {
    log.info("Checking if author with name: {}, already exists.", name);

    return authorRepository.existsByName(name);
  }

  @Transactional
  private void delete(Author author) {
    log.info("Deleting author with id: {} from database.", author.getId());
    authorRepository.delete(author);
  }

  @Transactional
  private Author save(Author author) {

    return authorRepository.save(author);
  }
}
