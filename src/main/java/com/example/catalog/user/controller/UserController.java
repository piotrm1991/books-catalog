package com.example.catalog.user.controller;

import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class handling user-related endpoints.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

  private final UserService userService;

  /**
   * Handles HTTP POST requests for creating a new user.
   *
   * @param userCreate The UserCreate request body containing user creation details.
   * @return A UserResponse representing the created user.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponse createUser(@Valid @RequestBody UserCreate userCreate) {
    log.info("POST-request: creating user with login: {}", userCreate.login());

    return  userService.createUser(userCreate);
  }

  /**
   * Handles HTTP GET requests to retrieve user information based on the provided user ID.
   *
   * @param id The unique identifier of the user.
   * @return The UserResponse object representing the user information.
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN') || @webSecurity.checkUserId(authentication,#id)")
  public UserResponse getUserById(@PathVariable Long id) {
    log.info("GET-request: getting user with id: {}", id);

    return userService.getUserResponseById(id);
  }

  /**
   * Handles GET requests to retrieve a paginated and sorted list of users based
   *           on the provided parameters.
   *
   * @param pageable      The pagination information.
   * @return A paginated and sorted list of user responses in response to the client's request.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public Page<UserResponse> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting all users.");

    return userService.getAllUsers(pageable);
  }

  /**
   * Handles HTTP PUT requests to update user information based on the provided user ID
   *                   and UserUpdate object.
   *
   * @param id         The unique identifier of the user to be updated.
   * @param userUpdate The UserUpdate object containing the updated user data,
   *                   validated based on specified constraints.
   * @return A UserResponse object representing the updated user information.
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdate userUpdate) {
    log.info("PUT-request: updating user with ID: {}", id);

    return userService.updateUser(id, userUpdate);
  }

  /**
   * Handles HTTP DELETE request for disabling a user account.
   * This endpoint deactivates the user account specified by the given ID.
   *
   * @param id The unique identifier of the user account to be disabled.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void disableUser(@PathVariable Long id) {
    log.info("DELETE-request: disabling user with id: {}", id);

    userService.disableUserById(id);
  }
}
