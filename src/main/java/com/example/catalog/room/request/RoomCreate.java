package com.example.catalog.room.request;

import static com.example.catalog.util.MessagesConstants.RoomNameAlreadyExistsMessage;
import static com.example.catalog.util.MessagesConstants.RoomNameCanNotBeBlankMessage;

import com.example.catalog.room.validator.RoomNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new room.
 */
public record RoomCreate(

    @NotBlank(message = RoomNameCanNotBeBlankMessage)
    @RoomNameAlreadyExists(message = RoomNameAlreadyExistsMessage)
    String name
) {}
