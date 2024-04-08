package com.example.catalog.room.request;

import static com.example.catalog.util.MessagesConstants.RoomNameCanNotBeBlank;

import com.example.catalog.room.validator.RoomNameAlreadyExists;
import javax.validation.constraints.NotBlank;

/**
 * A record representing the request for updating room's name.
 */
public record RoomUpdate(

    @NotBlank(message = RoomNameCanNotBeBlank)
    @RoomNameAlreadyExists
    String name
) {}
