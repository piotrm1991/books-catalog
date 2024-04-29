package com.example.catalog.room.request;

import static com.example.catalog.util.MessagesConstants.ROOM_NAME_ALREADY_EXISTS;
import static com.example.catalog.util.MessagesConstants.ROOM_NAME_CAN_NOT_BE_BLANK;

import com.example.catalog.room.validator.RoomNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for creating a new room.
 */
public record RoomCreate(

    @NotBlank(message = ROOM_NAME_CAN_NOT_BE_BLANK)
    @RoomNameAlreadyExists(message = ROOM_NAME_ALREADY_EXISTS)
    String name
) {}
