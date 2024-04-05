package com.example.catalog.user.service;

import static com.example.catalog.util.ErrorMessagesConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import com.example.catalog.date.service.DateService;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.exception.ValidationException;
import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.mapper.UserMapper;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private DateService dateService;

  private User user;

  private UserCreate userCreation;

  private UserResponse userResponse;

  private List<User> listOfUsers;

  private Pageable pageable;

  private UserUpdate userUpdate;

  private User updatedUser;

  private UserResponse updatedUserResponse;

  @BeforeEach
  void setUp() {
    user = UserHelper.createUser();

    userCreation = UserHelper.createUserCreate();

    userResponse = UserHelper.createUserResponse();

    listOfUsers = UserHelper.createListWithUsers(10);

    pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.asc("id")));

    userUpdate = UserHelper.createUserUpdate();

    updatedUser = UserHelper.createUpdatedUser();

    updatedUserResponse = UserHelper.createUpdatedUserResponse();
  }

  @Test
  void whenCorrectUserCreation_thenCorrect() {
    when(userMapper.mapUserCreateToEntity(userCreation)).thenReturn(user);
    when(userMapper.mapEntityToResponse(user)).thenReturn(userResponse);

    UserResponse actualUserResponse = userService.createUser(userCreation);

    assertEquals(userResponse, actualUserResponse);
  }

  @Test
  void givenExistingLogin_whenCheckIfExistsByLogin_thenTrue() {
    when(userRepository.existsByLogin(anyString())).thenReturn(true);

    boolean result = userService.checkIfExistsByLogin(anyString());

    assertTrue(result);
  }

  @Test
  void givenNotExistingLogin_whenCheckIfExistsByLogin_thenFalse() {
    when(userRepository.existsByLogin(anyString())).thenReturn(false);

    boolean result = userService.checkIfExistsByLogin(anyString());

    assertFalse(result);
  }

  @Test
  void whenSave_thenCorrect() {
    userService.save(user);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  void givenCorrectUserId_whenGetUserById_thenCorrect() {
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    User actualUser = userService.getUserById(user.getId());

    assertEquals(user, actualUser);
  }

  @Test
  void givenCorrectUserId_whenGetUserResponseById_thenCorrect() {
    when(userRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.getUserById(999L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(createEntityNotExistsMessage(User.class.getSimpleName(), 999L));
  }

  @Test
  public void givenCorrectUserResponseId_whenGetUserResponseById_thenCorrect() {
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(userMapper.mapEntityToResponse(user)).thenReturn(userResponse);

    UserResponse actualUserResponse = userService.getUserResponseById(user.getId());

    assertEquals(userResponse, actualUserResponse);
  }

  @Test
  public void givenIncorrectUserResponseId_whenGetUserResponseById_thenNotFound() {
    Long incorrectId = 999L;
    when(userRepository.findById(incorrectId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.getUserResponseById(incorrectId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(createEntityNotExistsMessage(User.class.getSimpleName(), incorrectId));
  }

  @Test
  public void givenGetFirstPageOfUsers_whenGetAllUsers_thenCorrect() {
    when(userRepository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl<>(listOfUsers));

    Page<UserResponse> result = userService.getAllUsers(pageable);
    UserResponse expectedUser = userMapper.mapEntityToResponse(listOfUsers.get(0));

    assertEquals(listOfUsers.size(), result.getTotalElements());
    assertEquals(expectedUser, result.getContent().get(0));
  }

  @Test
  public void givenGetSecondPageOfUsers_whenGetAllUsers_thenCorrect() {
    Page<User> usersPage = new PageImpl<>(listOfUsers);
    Pageable pageable = PageRequest.of(1, 3, Sort.by(Sort.Order.asc("id")));

    when(userRepository.findAll(any(PageRequest.class))).thenReturn(usersPage);

    Page<UserResponse> result = userService.getAllUsers(pageable);
    UserResponse expectedUser = userMapper.mapEntityToResponse(listOfUsers.get(3));

    assertEquals(listOfUsers.size(), result.getTotalElements());
    assertEquals(expectedUser, result.getContent().get(0));
  }

  @Test
  void givenCorrectUserUpdate_whenCorrectUserUpdate_thenCorrect() {

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(userMapper.mapUserUpdateToEntity(user, userUpdate)).thenReturn(updatedUser);
    when(userMapper.mapEntityToResponse(updatedUser)).thenReturn(updatedUserResponse);

    UserResponse actualUserResponse = userService.updateUser(user.getId(), userUpdate);

    assertEquals(updatedUserResponse, actualUserResponse);
    assertEquals(UserStatusEnum.DISABLED, updatedUser.getStatus());
    verify(userRepository).save(updatedUser);
  }

  @Test
  void givenNonExistentUserId_whenUpdateUser_thenThrowException() {
    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user.getId(), userUpdate));
    verify(userRepository, never()).save(any());
  }

  @Test
  void givenInvalidUserData_whenUpdateUser_thenThrowException() {
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(userMapper.mapUserUpdateToEntity(user, userUpdate)).thenThrow(ValidationException.class);

    assertThrows(ValidationException.class, () -> userService.updateUser(user.getId(), userUpdate));
    verify(userRepository, never()).save(any());
  }

  @Test
  void givenValidUserId_whenDisableUserById_thenCorrect() {
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    userService.disableUserById(user.getId());

    assertEquals(UserStatusEnum.DISABLED, user.getStatus());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void givenNonExistentUserId_whenDisableUserById_thenThrowException() {
    Long userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> userService.disableUserById(userId));
    verify(userRepository, never()).save(any(User.class));
  }
}
