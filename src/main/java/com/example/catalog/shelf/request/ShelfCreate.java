package com.example.catalog.shelf.request;

import static com.example.catalog.util.MessagesConstants.SHELF_LETTER_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.SHELF_NUMBER_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.SHELF_ROOM_CAN_NOT_BE_NULL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A record representing the request for creating a new author.
 */
public record ShelfCreate(

    @NotBlank(message = SHELF_LETTER_CAN_NOT_BE_BLANK)
    String letter,

    @NotNull(message = SHELF_NUMBER_CAN_NOT_BE_BLANK)
    Integer number,

    @NotNull(message = SHELF_ROOM_CAN_NOT_BE_NULL)
    Long idRoom
) {}
