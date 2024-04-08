package com.example.catalog.publisher.request;

import static com.example.catalog.util.MessagesConstants.PublisherNameCanNotBeBlank;

import com.example.catalog.publisher.validator.PublisherNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating publisher's name.
 */
public record PublisherUpdate(

    @NotBlank(message = PublisherNameCanNotBeBlank)
    @PublisherNameAlreadyExists
    String name
) {}
