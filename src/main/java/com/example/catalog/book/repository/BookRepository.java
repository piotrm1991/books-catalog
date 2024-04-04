package com.example.catalog.book.repository;

import com.example.catalog.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Book entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  /**
   * Checks if book with the given name exists.
   *
   * @param title The title to check for existence.
   * @return True if author with the name exists, false otherwise.
   */
  boolean existsByTitle(String title);
}
