package com.example.catalog.author.repository;

import com.example.catalog.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Author entities.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  /**
   * Checks if author with the given name exists.
   *
   * @param name The name to check for existence.
   * @return True if author with the name exists, false otherwise.
   */
  boolean existsByName(String name);
}
