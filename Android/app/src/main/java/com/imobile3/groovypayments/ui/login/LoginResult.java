package com.imobile3.groovypayments.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result: success (user details) or error message.
 */
public class LoginResult {

    @Nullable
    private Integer error;
    @Nullable
    private String errorMessage;
    @Nullable
    private LoggedInUserView success;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable String error) {
        this.errorMessage = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    public LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    @Nullable
    String getErrorMessage() {
        return errorMessage;
    }
}
