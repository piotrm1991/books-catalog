package com.example.catalog.room.repository;

import com.example.catalog.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Room entities.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  /**
   * Checks if room with the given name exists.
   *
   * @param name The name to check for existence.
   * @return True if room with the name exists, false otherwise.
   */
  boolean existsByName(String name);
}
