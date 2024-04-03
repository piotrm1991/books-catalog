package com.example.catalog.shelf.service.impl;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.service.RoomService;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.shelf.mapper.ShelfMapper;
import com.example.catalog.shelf.repository.ShelfRepository;
import com.example.catalog.shelf.request.ShelfCreate;
import com.example.catalog.shelf.request.ShelfUpdate;
import com.example.catalog.shelf.response.ShelfResponse;
import com.example.catalog.shelf.service.ShelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.catalog.util.ErrorMessagesConstants.createShelfNotExistMessage;

/**
 * Implementation of the ShelfService interface for managing shelf requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {

  private final ShelfRepository shelfRepository;
  private final ShelfMapper shelfMapper;
  private final RoomService roomService;

  @Override
  public ShelfResponse createShelf(ShelfCreate shelfCreate) {
    Room room = roomService.getRoomById(shelfCreate.idRoom());

    log.info("Creating shelf with letter: {}, and number: {} in the room: {}", shelfCreate.letter(), shelfCreate.number(), room.getName());

    Shelf shelf = shelfMapper.mapShelfCreateToEntity(shelfCreate);
    shelf.setRoom(room);
    shelf = save(shelf);

    return shelfMapper.mapEntityToResponse(shelf);
  }

  @Override
  public ShelfResponse updateShelf(Long id, ShelfUpdate shelfUpdate) {
    log.info("Updating shelf with id: {}", id);
    Shelf shelfToUpdate = getShelfById(id);
    shelfToUpdate = shelfMapper.mapShelfUpdateToEntity(shelfToUpdate, shelfUpdate);
    shelfToUpdate = save(shelfToUpdate);

    return shelfMapper.mapEntityToResponse(shelfToUpdate);
  }

  @Override
  public ShelfResponse getShelfResponseById(Long id) {
    log.info("Getting shelf with id: {}", id);

    return shelfMapper.mapEntityToResponse(getShelfById(id));
  }

  @Override
  public Page<ShelfResponse> getAllShelvesPage(Pageable pageable) {
    log.info("Getting all shelves from database.");

    return shelfRepository.findAll(pageable).map(shelfMapper::mapEntityToResponse);
  }

  @Override
  public void deleteShelfById(Long id) {
    log.info("Deleting shelf with id: {}", id);
    delete(getShelfById(id));
  }

  @Override
  public Shelf getShelfById(Long id) {
    log.info("Getting shelf from database with id: {}", id);

    return shelfRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(createShelfNotExistMessage(id)));
  }

  @Transactional
  private void delete(Shelf shelf) {
    log.info("Deleting shelf with id: {} from database.", shelf.getId());
    shelfRepository.delete(shelf);
  }

  @Transactional
  private Shelf save(Shelf shelf) {

    return shelfRepository.save(shelf);
  }
}
