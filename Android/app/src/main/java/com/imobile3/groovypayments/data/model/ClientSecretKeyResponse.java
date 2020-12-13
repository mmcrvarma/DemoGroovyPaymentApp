package com.imobile3.groovypayments.data.model;

public class ClientSecretKeyResponse {
    private String errorMessage = null;
    private String secretKey = null;

    public ClientSecretKeyResponse(String error, String key)
    {
        errorMessage = error;
        secretKey = key;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public String getSecretKey()
    {
        return secretKey;
    }
}
