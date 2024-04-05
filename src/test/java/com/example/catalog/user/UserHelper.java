package com.example.catalog.user;

import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserHelper {
  public static final String userUrlPath = "/users";
  public static final Long testUsersCount = 10L;

  public static final Long id = 1L;
  public static final String login = "LoginTest";
  public static final String password = "P4$$W0rD1";
  public static final UserRoleEnum role = UserRoleEnum.ADMIN;
  public static final UserStatusEnum status = UserStatusEnum.ENABLED;
  public static final LocalDate createDate = LocalDate.now();
  public static final LocalDate updateDate = LocalDate.now();
  public static final LocalDate deleteDate = LocalDate.now();
  public static final String updateLogin = "LoginTestUpdated";
  private static final String updatePassword = "PassWor$$1";
  private static final UserRoleEnum updateRole = UserRoleEnum.ADMIN;
  private static final UserStatusEnum updateStatus = UserStatusEnum.DISABLED;

  public static User createUser() {

    return User.builder()
        .login(login)
        .password(password)
        .role(role)
        .status(status)
        .createDate(createDate)
        .build();
  }

  public static UserCreate createUserCreate() {

    return new UserCreate(
        login,
        password,
        password,
        role
    );
  }

  public static UserResponse createUserResponse() {

    return new UserResponse(
        id,
        login,
        role,
        status,
        createDate,
        updateDate,
        deleteDate
    );
}
  public static UserUpdate createUserUpdate() {

    return new UserUpdate(
        updateLogin,
        updatePassword,
        updatePassword,
        updateRole,
        updateStatus
    );
  }

  public static List<User> createListWithUsers(int numberOfUsers) {

    List<User> users = new ArrayList<>();
    if (numberOfUsers > 0) {
        for (int i = 1; i <= numberOfUsers; i++) {
            users.add(
                User.builder()
                    .login(login + i)
                    .password(password)
                    .role(role)
                    .status(status)
                    .createDate(createDate)
                    .build()
            );
        }
    }

    return users;
  }

  public static User createUpdatedUser() {

    return User.builder()
        .login(updateLogin)
        .password(updatePassword)
        .role(updateRole)
        .status(updateStatus)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }

  public static UserResponse createUpdatedUserResponse() {

    return new UserResponse(
        id,
        updateLogin,
        updateRole,
        updateStatus,
        createDate,
        updateDate,
        deleteDate
    );
  }

  public static List<User> prepareUserList() {


    List<User> users = new ArrayList<>();
    if (testUsersCount > 0) {
      for (int i = 1; i <= testUsersCount; i++) {
        users.add(
            User.builder()
                .login(login + i)
                .password(password)
                .role(role)
                .status(status)
                .createDate(createDate)
                .build()
        );
      }
    }

    return users;
  }

  public static UserCreate createUserCreateBlankLogin() {

    return new UserCreate(
        "",
        password,
        password,
        role
    );
  }

  public static UserUpdate createUserUpdateBlankLogin() {

    return new UserUpdate(
        "",
        updatePassword,
        updatePassword,
        updateRole,
        updateStatus
    );
  }

  public static UserUpdate createUserUpdateWithExistingLogin() {

    return new UserUpdate(
        login,
        updatePassword,
        updatePassword,
        updateRole,
        updateStatus
    );
  }
}
