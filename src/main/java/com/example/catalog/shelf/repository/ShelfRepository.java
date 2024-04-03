package com.example.catalog.shelf.repository;

import com.example.catalog.shelf.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Author entities.
 */
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {}
