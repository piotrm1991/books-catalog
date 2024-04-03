package com.example.catalog.shelf.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.example.catalog.util.ErrorMessagesConstants.*;

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
