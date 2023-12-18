package com.example.catalog.publisher.request;

import static com.example.catalog.util.ErrorMessagesConstants.PublisherNameCanNotBeBlank;

import com.example.catalog.publisher.validator.PublisherNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new publisher.
 */
public record PublisherCreate(

    @NotBlank(message = PublisherNameCanNotBeBlank)
    @PublisherNameAlreadyExists
    String name
) {}
