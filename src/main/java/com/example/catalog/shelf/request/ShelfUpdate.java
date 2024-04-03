package com.example.catalog.shelf.request;

/**
 * A record representing the request for updating shelf's name.
 */
public record ShelfUpdate(
    String letter,

    Integer number,

    Long idRoom
) {}
