package com.imobile3.groovypayments.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String displayName;
    private String userId;
    private String userEmail;
    private String userName;

    public LoggedInUser(String userId, String displayName, String userEmail, String userName) {
        this.userId = userId;
        this.displayName = displayName;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }
}
