package com.example.catalog.author.request;

import static com.example.catalog.util.ErrorMessagesConstants.AuthorNameCanNotBeBlank;

import com.example.catalog.author.validator.AuthorNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating author's name.
 */
public record AuthorUpdate(

    @NotBlank(message = AuthorNameCanNotBeBlank)
    @AuthorNameAlreadyExists
    String name
) {}
