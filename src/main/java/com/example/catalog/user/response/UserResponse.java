package com.example.catalog.user.response;

import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import java.time.LocalDate;

/**
 * Represents the response object containing user information.
 */
public record UserResponse(
        Long id,

        String login,

        UserRoleEnum role,

        UserStatusEnum status,

        LocalDate createDate,

        LocalDate updateDate,

        LocalDate deleteDate
) {
}
