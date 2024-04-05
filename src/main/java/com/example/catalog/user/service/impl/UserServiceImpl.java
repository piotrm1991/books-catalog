package com.example.catalog.user.service.impl;

import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static com.example.catalog.util.ErrorMessagesConstants.createUserWithLoginNotExistsMessage;

import com.example.catalog.date.service.DateService;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.mapper.UserMapper;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.user.service.UserService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service implementation for user-related operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final DateService dateService;

  @Override
  @Transactional
  public void disableUserById(Long id) {
    log.info("Disabling user with ID: {}", id);

    User user = getUserById(id);
    user.setStatus(UserStatusEnum.DISABLED);
    user.setDeleteDate(dateService.getCurrentDate());
    save(user);
  }

  @Override
  @Transactional
  public UserResponse updateUser(Long id, UserUpdate userUpdate) {
    log.info("Updating user with ID: {}", id);

    User existingUser = getUserById(id);
    User updatedUser = userMapper.mapUserUpdateToEntity(existingUser, userUpdate);
    updatedUser.setUpdateDate(dateService.getCurrentDate());
    save(updatedUser);

    return userMapper.mapEntityToResponse(updatedUser);
  }

  @Override
  public Page<UserResponse> getAllUsers(Pageable pageable) {
    log.info("Getting all users and sorting them by (Pageable pageable): {}", pageable);

    return userRepository.findAll(pageable).map(userMapper::mapEntityToResponse);
  }

  @Override
  @Transactional
  public UserResponse getUserResponseById(Long id) {
    log.info("Getting user from database with id: {}", id);

    return userMapper.mapEntityToResponse(getUserById(id));
  }

  @Override
  @Transactional
  public UserResponse createUser(UserCreate userCreate) {
    log.info("Creating new user with login: {}", userCreate.login());

    User user = userMapper.mapUserCreateToEntity(userCreate);
    user.setStatus(UserStatusEnum.ENABLED);
    user.setCreateDate(dateService.getCurrentDate());
    user.setUpdateDate(dateService.getCurrentDate());
    //TODO: password encoder
    save(user);

    return userMapper.mapEntityToResponse(user);
  }

  @Override
  public boolean checkIfExistsByLogin(String login) {
    log.info("Checking if login: {} is already in database.", login);

    return userRepository.existsByLogin(login);
  }

  @Override
  @Transactional
  public User getUserById(Long userId) {
    log.info("Getting user from database with id: {}", userId);

    return userRepository.findById(userId).orElseThrow(()
        -> new EntityNotFoundException(
                createEntityNotExistsMessage(User.class.getSimpleName(), userId)));
  }

  @Override
  @Transactional
  public User save(User user) {
    log.info("Saving user with login: {} into database.", user.getLogin());

    return userRepository.save(user);
  }

  @Override
  public User getUserByLogin(String login) {
    log.info("Searching User by login: {}", login);

    return userRepository.findByLogin(login).orElseThrow(()
        -> new EntityNotFoundException(
        createUserWithLoginNotExistsMessage(User.class.getSimpleName(), login)));
  }
}
