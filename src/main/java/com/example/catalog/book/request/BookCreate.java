package com.example.catalog.book.request;

import static com.example.catalog.util.MessagesConstants.AUTHOR_CAN_NOT_BE_NULL;
import static com.example.catalog.util.MessagesConstants.BOOK_TITLE_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.PUBLISHER_CAN_NOT_BE_NULL;
import static com.example.catalog.util.MessagesConstants.SHELF_CAN_NOT_BE_NULL;
import static com.example.catalog.util.MessagesConstants.STATUS_TYPE_CAN_NOT_BE_BLANK;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A record representing the request for creating a new author.
 */
public record BookCreate(

    @NotBlank(message = BOOK_TITLE_CAN_NOT_BE_BLANK)
    String title,

    @NotNull(message = AUTHOR_CAN_NOT_BE_NULL)
    Long authorId,

    @NotNull(message = PUBLISHER_CAN_NOT_BE_NULL)
    Long publisherId,

    @NotNull(message = SHELF_CAN_NOT_BE_NULL)
    Long shelfId,

    @NotNull(message = STATUS_TYPE_CAN_NOT_BE_BLANK)
    Long statusTypeId
) {}
