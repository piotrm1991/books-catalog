package com.example.catalog.room.service.impl;

import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.room.entity.Room;
import com.example.catalog.room.mapper.RoomMapper;
import com.example.catalog.room.repository.RoomRepository;
import com.example.catalog.room.request.RoomCreate;
import com.example.catalog.room.request.RoomUpdate;
import com.example.catalog.room.response.RoomResponse;
import com.example.catalog.room.service.RoomService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the RoomService interface for managing room requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  @Override
  public RoomResponse createRoom(RoomCreate roomCreate) {
    log.info("Creating room with name: {}", roomCreate.name());
    Room room = roomMapper.mapRoomCreateToEntity(roomCreate);
    room = save(room);

    return roomMapper.mapEntityToResponse(room);
  }

  @Override
  public RoomResponse updateRoom(Long id, RoomUpdate roomUpdate) {
    log.info("Updating room with id: {}", id);
    Room roomToUpdate = getRoomById(id);
    roomToUpdate.setName(roomUpdate.name());
    roomToUpdate = save(roomToUpdate);

    return roomMapper.mapEntityToResponse(roomToUpdate);
  }

  @Override
  public RoomResponse getRoomResponseById(Long id) {
    log.info("Getting room with id: {}", id);

    return roomMapper.mapEntityToResponse(getRoomById(id));
  }

  @Override
  public Page<RoomResponse> getAllRoomsPage(Pageable pageable) {
    log.info("Getting all rooms from database.");

    return roomRepository.findAll(pageable).map(roomMapper::mapEntityToResponse);
  }

  //TODO: Add check if there are books added to this room
  @Override
  public void deleteRoomById(Long id) {
    log.info("Deleting room with id: {}", id);
    delete(getRoomById(id));
  }

  @Override
  public Room getRoomById(Long id) {
    log.info("Getting room from database with id: {}", id);

    return roomRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(
                    createEntityNotExistsMessage(Room.class.getSimpleName(), id)));
  }

  @Override
  public boolean checkOfNameAlreadyExists(String name) {
    log.info("Checking if room with name: {}, already exists.", name);

    return roomRepository.existsByName(name);
  }

  @Transactional
  private void delete(Room room) {
    log.info("Deleting room with id: {} from database.", room.getId());
    roomRepository.delete(room);
  }

  @Transactional
  private Room save(Room room) {

    return roomRepository.save(room);
  }
}
