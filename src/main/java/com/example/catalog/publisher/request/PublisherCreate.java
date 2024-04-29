package com.example.catalog.publisher.request;

import static com.example.catalog.util.MessagesConstants.PUBLISHER_WITH_THIS_NAME_ALREADY_EXISTS;
import static com.example.catalog.util.MessagesConstants.PUBLISHER_NAME_CAN_NOT_BE_BLANK;

import com.example.catalog.publisher.validator.PublisherNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new publisher.
 */
public record PublisherCreate(

    @NotBlank(message = PUBLISHER_NAME_CAN_NOT_BE_BLANK)
    @PublisherNameAlreadyExists(message = PUBLISHER_WITH_THIS_NAME_ALREADY_EXISTS)
    String name
) {}
