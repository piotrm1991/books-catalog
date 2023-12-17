package com.example.catalog.author.request;

import static com.example.catalog.util.ErrorMessagesConstants.AuthorNameCanNotBeBlank;

import com.example.catalog.author.validator.NameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating author's name.
 */
public record AuthorUpdate(

    @NotBlank(message = AuthorNameCanNotBeBlank)
    @NameAlreadyExists
    String name
) {}
