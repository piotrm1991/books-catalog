package com.example.catalog.user.repository;

import com.example.catalog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Checks if a user with the given login exists.
   *
   * @param login The login to check for existence.
   * @return True if a user with the email exists, false otherwise.
   */
  boolean existsByLogin(String login);
}
