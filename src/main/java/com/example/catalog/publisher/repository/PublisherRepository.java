package com.example.catalog.publisher.repository;

import com.example.catalog.publisher.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Publisher entities.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

  /**
   * Checks if publisher with the given name exists.
   *
   * @param name The name to check for existence.
   * @return True if publisher with the name exists, false otherwise.
   */
  boolean existsByName(String name);
}
