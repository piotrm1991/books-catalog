package com.example.catalog.publisher.service.impl;

import static com.example.catalog.util.ExceptionMessagesConstants.createEntityNotExistsMessage;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.publisher.mapper.PublisherMapper;
import com.example.catalog.publisher.repository.PublisherRepository;
import com.example.catalog.publisher.request.PublisherCreate;
import com.example.catalog.publisher.request.PublisherUpdate;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.publisher.service.PublisherService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the PublisherService interface for managing publisher requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

  private final PublisherRepository publisherRepository;
  private final PublisherMapper publisherMapper;

  @Override
  public PublisherResponse createPublisher(PublisherCreate publisherCreate) {
    log.info("Creating publisher with name: {}", publisherCreate.name());
    Publisher publisher = publisherMapper.mapPublisherCreateToEntity(publisherCreate);
    publisher = save(publisher);

    return publisherMapper.mapEntityToResponse(publisher);
  }

  @Override
  public PublisherResponse updatePublisher(Long id, PublisherUpdate publisherUpdate) {
    log.info("Updating publisher with id: {}", id);
    Publisher publisherToUpdate = getPublisherById(id);
    publisherToUpdate.setName(publisherUpdate.name());
    publisherToUpdate = save(publisherToUpdate);

    return publisherMapper.mapEntityToResponse(publisherToUpdate);
  }

  @Override
  public PublisherResponse getPublisherResponseById(Long id) {
    log.info("Getting publisher with id: {}", id);

    return publisherMapper.mapEntityToResponse(getPublisherById(id));
  }

  @Override
  public Page<PublisherResponse> getAllPublishersPage(Pageable pageable) {
    log.info("Getting all publishers from database.");

    return publisherRepository.findAll(pageable).map(publisherMapper::mapEntityToResponse);
  }

  //TODO: Add check if there are books added to this publisher
  @Override
  public void deletePublisherById(Long id) {
    log.info("Deleting publisher with id: {}", id);
    delete(getPublisherById(id));
  }

  @Override
  public Publisher getPublisherById(Long id) {
    log.info("Getting publisher from database with id: {}", id);

    return publisherRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(
                    createEntityNotExistsMessage(Publisher.class.getSimpleName(), id)));
  }

  @Override
  public boolean checkOfNameAlreadyExists(String name) {
    log.info("Checking if publisher with name: {}, already exists.", name);

    return publisherRepository.existsByName(name);
  }

  @Override
  public List<PublisherResponse> getAllPublishersList() {
    log.info("Getting all publishers to the list.");

    return publisherRepository
          .findAll()
          .stream()
          .map(publisherMapper::mapEntityToResponse)
          .collect(Collectors.toList());
  }

  @Transactional
  private void delete(Publisher publisher) {
    log.info("Deleting publisher with id: {} from database.", publisher.getId());
    publisherRepository.delete(publisher);
  }

  @Transactional
  private Publisher save(Publisher publisher) {

    return publisherRepository.save(publisher);
  }
}
