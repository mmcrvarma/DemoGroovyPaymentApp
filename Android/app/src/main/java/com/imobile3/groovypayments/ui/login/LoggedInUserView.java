package com.imobile3.groovypayments.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {

    private String displayName;
    private String userEmail;
    private String userName;

    LoggedInUserView(String displayName, String userEmail, String userName) {
        this.displayName = displayName;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    String getDisplayName() {
        return displayName;
    }
    String getUserEmail() {
        return userEmail;
    }
    String getUserName() {
        return userName;
    }
}
