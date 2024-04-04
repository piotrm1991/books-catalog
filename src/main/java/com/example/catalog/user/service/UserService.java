package com.example.catalog.user.service;

import com.example.catalog.user.request.UserCreate;
import com.example.catalog.user.request.UserUpdate;
import com.example.catalog.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

  /**
   * Check if provided login already exists in the system.
   *
   * @param login String login to check.
   * @return True if exists, False if not.
   */
  boolean checkIfExistsByLogin(String login);

  /**
   * Disables user account based on their ID.
   *
   * @param id The unique identifier of the user account to be deactivated.
   */
  void disableUserById(Long id);

  /**
   * Updates the user details based on the provided User object and UserUpdate request.
   *
   * @param id       The ID of user to be updated.
   * @param userUpdate The UserUpdate object containing updated user details.
   * @return A UserResponse object containing the updated user details.
   */
  UserResponse updateUser(Long id, UserUpdate userUpdate);

  /**
   * Retrieve a paginated list of user responses.
   *
   * @param pageable The pagination information.
   * @return A {@link Page} containing user responses.
   */
  Page<UserResponse> getAllUsers(Pageable pageable);

  /**
   * Retrieves a UserResponse object based on the user from the database with ID.
   * Utilizes the getById method to retrieve the user from the database and map it to UserResponse.
   *
   * @param id The identifier of the user.
   * @return The UserResponse object representing the user with the specified identifier.
   */
  UserResponse getUserResponseById(Long id);

  /**
   * Creates a new user based on the provided UserCreate request.
   *
   * @param userCreate The UserCreate request containing user details.
   * @return A UserResponse representing the created user.
   */
  UserResponse createUser(UserCreate userCreate);
}
