package com.example.catalog.book.request;

import static com.example.catalog.util.MessagesConstants.BookTitleCanNotBeBlank;

import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating author's name.
 */
public record BookUpdate(

    @NotBlank(message = BookTitleCanNotBeBlank)
    String title,

    Long authorId,

    Long publisherId,

    Long shelfId,

    Long statusTypeId
) {}
