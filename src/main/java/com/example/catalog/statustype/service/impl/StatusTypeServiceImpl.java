package com.example.catalog.statustype.service.impl;

import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;

import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.statustype.entity.StatusType;
import com.example.catalog.statustype.mapper.StatusTypeMapper;
import com.example.catalog.statustype.repository.StatusTypeRepository;
import com.example.catalog.statustype.request.StatusTypeCreate;
import com.example.catalog.statustype.request.StatusTypeUpdate;
import com.example.catalog.statustype.response.StatusTypeResponse;
import com.example.catalog.statustype.service.StatusTypeService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the StatusTypeService interface for managing statusType requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeServiceImpl implements StatusTypeService {

  private final StatusTypeRepository statusTypeRepository;
  private final StatusTypeMapper statusTypeMapper;

  @Override
  public StatusTypeResponse createStatusType(StatusTypeCreate statusTypeCreate) {
    log.info("Creating statusType with name: {}", statusTypeCreate.name());
    StatusType statusType = statusTypeMapper.mapStatusTypeCreateToEntity(statusTypeCreate);
    statusType = save(statusType);

    return statusTypeMapper.mapEntityToResponse(statusType);
  }

  @Override
  public StatusTypeResponse updateStatusType(Long id, StatusTypeUpdate statusTypeUpdate) {
    log.info("Updating statusType with id: {}", id);
    StatusType statusTypeToUpdate = getStatusTypeById(id);
    statusTypeToUpdate.setName(statusTypeUpdate.name());
    statusTypeToUpdate = save(statusTypeToUpdate);

    return statusTypeMapper.mapEntityToResponse(statusTypeToUpdate);
  }

  @Override
  public StatusTypeResponse getStatusTypeResponseById(Long id) {
    log.info("Getting statusType with id: {}", id);

    return statusTypeMapper.mapEntityToResponse(getStatusTypeById(id));
  }

  @Override
  public Page<StatusTypeResponse> getAllStatusTypesPage(Pageable pageable) {
    log.info("Getting all statusTypes from database.");

    return statusTypeRepository.findAll(pageable).map(statusTypeMapper::mapEntityToResponse);
  }

  //TODO: Add check if there are books added to this statusType
  @Override
  public void deleteStatusTypeById(Long id) {
    log.info("Deleting statusType with id: {}", id);
    delete(getStatusTypeById(id));
  }

  @Override
  public StatusType getStatusTypeById(Long id) {
    log.info("Getting statusType from database with id: {}", id);

    return statusTypeRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(
                    createEntityNotExistsMessage(StatusType.class.getSimpleName(), id)));
  }

  @Override
  public boolean checkOfNameAlreadyExists(String name) {
    log.info("Checking if statusType with name: {}, already exists.", name);

    return statusTypeRepository.existsByName(name);
  }

  @Transactional
  private void delete(StatusType statusType) {
    log.info("Deleting statusType with id: {} from database.", statusType.getId());
    statusTypeRepository.delete(statusType);
  }

  @Transactional
  private StatusType save(StatusType statusType) {

    return statusTypeRepository.save(statusType);
  }
}
