package com.example.catalog.shelf.response;

import com.example.catalog.room.response.RoomResponse;

/**
 * A record representing the response containing author information.
 */
public record ShelfResponse(
    Long id,

    String letter,

    Integer number,

    RoomResponse room
) {}
