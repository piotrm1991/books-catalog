package com.example.catalog.user.request;

import static com.example.catalog.util.ErrorMessagesConstants.ConfirmPasswordCanNotBeBlankErrorMessage;
import static com.example.catalog.util.ErrorMessagesConstants.LoginIsRequiredMessage;
import static com.example.catalog.util.ErrorMessagesConstants.LoginSizeMinMessage;
import static com.example.catalog.util.ErrorMessagesConstants.PasswordAndConfirmPasswordMatchingMessage;
import static com.example.catalog.util.ErrorMessagesConstants.PasswordCanNotBeBlankErrorMessage;
import static com.example.catalog.util.ErrorMessagesConstants.PasswordSizeMinMessage;

import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.validator.LoginAlreadyExists;
import com.example.catalog.user.validator.PasswordMatching;
import com.example.catalog.user.validator.StrongPassword;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Represents a request object for update user.
 */
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = PasswordAndConfirmPasswordMatchingMessage
)
public record UserUpdate(
        @NotBlank(message = LoginIsRequiredMessage)
        @Size(min = 6, message = LoginSizeMinMessage)
        @LoginAlreadyExists
        String login,

        @NotBlank(message = PasswordCanNotBeBlankErrorMessage)
        @Size(min = 8, message = PasswordSizeMinMessage)
        @StrongPassword
        String password,

        @NotBlank(message = ConfirmPasswordCanNotBeBlankErrorMessage)
        String confirmPassword,

        UserRoleEnum role,

        UserStatusEnum status
) {}
