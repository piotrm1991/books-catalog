package com.example.catalog.book.request;

import static com.example.catalog.util.MessagesConstants.BOOK_TITLE_CAN_NOT_BE_BLANK;

import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating author's name.
 */
public record BookUpdate(

    @NotBlank(message = BOOK_TITLE_CAN_NOT_BE_BLANK)
    String title,

    Long authorId,

    Long publisherId,

    Long shelfId,

    Long statusTypeId
) {}
