package com.example.catalog.statustype.request;

import static com.example.catalog.util.MessagesConstants.STATUS_TYPE_NAME_ALREADY_EXISTS;
import static com.example.catalog.util.MessagesConstants.STATUS_TYPE_NAME_CAN_NOT_BE_BLANK;

import com.example.catalog.statustype.validator.StatusTypeNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new statusType.
 */
public record StatusTypeCreate(

    @NotBlank(message = STATUS_TYPE_NAME_CAN_NOT_BE_BLANK)
    @StatusTypeNameAlreadyExists(message = STATUS_TYPE_NAME_ALREADY_EXISTS)
    String name
) {}
