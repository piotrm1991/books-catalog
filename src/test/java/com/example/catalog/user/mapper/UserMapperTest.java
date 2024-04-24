package com.example.catalog.user.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for mapping between User entity and record requests and responses.
 */
@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

  @InjectMocks
  private UserMapper userMapper;

  @Mock
  private ObjectMapper mapper;

  private User user;

  private UserCreate userCreate;
  private UserResponse userResponse;
  private UserUpdate userUpdate;

  @BeforeEach
  void setUp() {
    user = UserHelper.createUser();
    userCreate = UserHelper.createUserCreate();
    userResponse = UserHelper.createUserResponse();
    userUpdate = UserHelper.createUserUpdate();
  }

  @Test
  void givenCorrectUserCreation_whenMapUserCreationToEntity_thenCorrect()
        throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(userCreate), User.class)).thenReturn(user);

    User expectedUser = userMapper.mapUserCreateToEntity(userCreate);

    assertEquals(expectedUser, user);
  }

  @Test
  void givenCorrectUser_whenMapEntityToResponse_thenCorrect() throws JsonProcessingException {
    when(mapper.readValue(mapper.writeValueAsString(user), UserResponse.class))
          .thenReturn(userResponse);

    UserResponse expectedUserResponse = userMapper.mapEntityToResponse(user);

    assertEquals(expectedUserResponse, userResponse);
  }

  @Test
  void givenCorrectUserUpdate_whenMapUserUpdateToUser_thenCorrect() {
    User expectedUser = userMapper.mapUserUpdateToEntity(user, userUpdate);

    assertEquals(userUpdate.login(), expectedUser.getLogin());
    assertEquals(userUpdate.role(), expectedUser.getRole());
    assertEquals(userUpdate.status(), expectedUser.getStatus());
  }
}
