package com.example.catalog.publisher.request;

import static com.example.catalog.util.MessagesConstants.PUBLISHER_NAME_CAN_NOT_BE_BLANK;
import static com.example.catalog.util.MessagesConstants.PUBLISHER_WITH_THIS_NAME_ALREADY_EXISTS;

import com.example.catalog.publisher.validator.PublisherNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating publisher's name.
 */
public record PublisherUpdate(

    @NotBlank(message = PUBLISHER_NAME_CAN_NOT_BE_BLANK)
    @PublisherNameAlreadyExists(message = PUBLISHER_WITH_THIS_NAME_ALREADY_EXISTS)
    String name
) {}
