package com.example.catalog.statustype.request;

import static com.example.catalog.util.ErrorMessagesConstants.StatusTypeNameCanNotBeBlank;

import com.example.catalog.statustype.validator.StatusTypeNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating statusType's name.
 */
public record StatusTypeUpdate(

    @NotBlank(message = StatusTypeNameCanNotBeBlank)
    @StatusTypeNameAlreadyExists
    String name
) {}
