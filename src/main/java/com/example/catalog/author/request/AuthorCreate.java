package com.example.catalog.author.request;

import static com.example.catalog.util.MessagesConstants.AUTHOR_NAME_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.AUTHOR_WITH_THIS_NAME_ALREADY_EXISTS;

import com.example.catalog.author.validator.AuthorNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new author.
 */
public record AuthorCreate(

    @NotBlank(message = AUTHOR_NAME_CAN_NOT_BE_BLANK)
    @AuthorNameAlreadyExists(message = AUTHOR_WITH_THIS_NAME_ALREADY_EXISTS)
    String name
) {}
