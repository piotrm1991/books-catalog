package com.example.catalog.shelf.request;

import static com.example.catalog.util.MessagesConstants.ShelfLetterNotBeBlank;
import static com.example.catalog.util.MessagesConstants.ShelfNumberNotBeBlank;
import static com.example.catalog.util.MessagesConstants.ShelfRoomNotBeBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A record representing the request for creating a new author.
 */
public record ShelfCreate(

    @NotBlank(message = ShelfLetterNotBeBlank)
    String letter,

    @NotNull(message = ShelfNumberNotBeBlank)
    Integer number,

    @NotNull(message = ShelfRoomNotBeBlank)
    Long idRoom
) {}
