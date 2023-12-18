package com.example.catalog.statustype.repository;

import com.example.catalog.statustype.entity.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on StatusType entities.
 */
@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, Long> {

  /**
   * Checks if statusType with the given name exists.
   *
   * @param name The name to check for existence.
   * @return True if statusType with the name exists, false otherwise.
   */
  boolean existsByName(String name);
}
