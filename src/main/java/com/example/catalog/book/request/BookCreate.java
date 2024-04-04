package com.example.catalog.book.request;

import static com.example.catalog.util.ErrorMessagesConstants.AuthorCanNotBeBlank;
import static com.example.catalog.util.ErrorMessagesConstants.BookTitleCanNotBeBlank;
import static com.example.catalog.util.ErrorMessagesConstants.StatusTypeCanNotBeBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A record representing the request for creating a new author.
 */
public record BookCreate(

    @NotBlank(message = BookTitleCanNotBeBlank)
    String title,

    @NotNull(message = AuthorCanNotBeBlank)
    Long authorId,

    Long publisherId,

    Long shelfId,

    @NotNull(message = StatusTypeCanNotBeBlank)
    Long statusTypeId
) {}
