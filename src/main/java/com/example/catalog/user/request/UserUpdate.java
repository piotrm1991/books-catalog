package com.example.catalog.user.request;

import static com.example.catalog.util.AuthenticationMessageConstants.LOGIN_IS_REQUIRED;
import static com.example.catalog.util.AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE;

import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.validator.LoginAlreadyExists;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents a request object for update user.
 */
@LoginAlreadyExists
public record UserUpdate(

        @NotNull(message = "Unexpected error!")
        Long id,

        @NotBlank(message = LOGIN_IS_REQUIRED)
        @Size(min = 4, message = LOGIN_SIZE_MIN_MESSAGE)
        String login,

        @NotNull(message = "You have to choose user role.")
        UserRoleEnum role,

        @NotNull(message = "You have to choose user status.")
        UserStatusEnum status
) {}
