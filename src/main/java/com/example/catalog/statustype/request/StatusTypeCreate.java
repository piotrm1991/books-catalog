package com.example.catalog.statustype.request;

import static com.example.catalog.util.MessagesConstants.StatusTypeNameCanNotBeBlank;

import com.example.catalog.statustype.validator.StatusTypeNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new statusType.
 */
public record StatusTypeCreate(

    @NotBlank(message = StatusTypeNameCanNotBeBlank)
    @StatusTypeNameAlreadyExists
    String name
) {}
