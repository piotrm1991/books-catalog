package com.example.catalog.author.request;

import static com.example.catalog.util.MessagesConstants.AuthorNameAlreadyExistsMessage;
import static com.example.catalog.util.MessagesConstants.AuthorNameCanNotBeBlankMessage;

import com.example.catalog.author.validator.AuthorNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new author.
 */
public record AuthorCreate(

    @NotBlank(message = AuthorNameCanNotBeBlankMessage)
    @AuthorNameAlreadyExists(message = AuthorNameAlreadyExistsMessage)
    String name
) {}
