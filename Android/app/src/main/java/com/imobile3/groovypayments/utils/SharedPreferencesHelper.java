package com.imobile3.groovypayments.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.imobile3.groovypayments.MainApplication;

public class SharedPreferencesHelper {
    private final String SHARED_PREFS_FILE_NAME = "shared_prefs";
    private final String SHARED_PREFS_LOGGED_IN_STATUS_KEY = "user_loggedIn";
    private final String SHARED_PREFS_DISPLAY_NAME = "user_display_name";
    private final String SHARED_PREFS_USER_EMAIL = "user_email";
    private final String SHARED_PREFS_USER_NAME = "user_name";

    private static SharedPreferencesHelper mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPreferencesHelper()
    {
        mSharedPreferences = MainApplication.getInstance()
                .getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesHelper getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new SharedPreferencesHelper();
        }

        return mInstance;
    }

    public void setLoggedInStatus(boolean loggedInStatus)
    {
        SharedPreferences.Editor mEditor =  mSharedPreferences.edit();
        mEditor.putBoolean(SHARED_PREFS_LOGGED_IN_STATUS_KEY, loggedInStatus);
        mEditor.apply();
    }

    public boolean getLoggedInStatus()
    {
        return mSharedPreferences.getBoolean(SHARED_PREFS_LOGGED_IN_STATUS_KEY, false);
    }

    public void setDisplayName(String displayName)
    {
        SharedPreferences.Editor mEditor =  mSharedPreferences.edit();
        mEditor.putString(SHARED_PREFS_DISPLAY_NAME, displayName);
        mEditor.apply();
    }

    public String getDisplayName()
    {
        return mSharedPreferences.getString(SHARED_PREFS_DISPLAY_NAME, "");
    }

    public void setEmail(String email)
    {
        SharedPreferences.Editor mEditor =  mSharedPreferences.edit();
        mEditor.putString(SHARED_PREFS_USER_EMAIL, email);
        mEditor.apply();
    }

    public String getEmail()
    {
        return mSharedPreferences.getString(SHARED_PREFS_USER_EMAIL, "");
    }

    public void setUserName(String userName)
    {
        SharedPreferences.Editor mEditor =  mSharedPreferences.edit();
        mEditor.putString(SHARED_PREFS_USER_NAME, userName);
        mEditor.apply();
    }

    public String getUserName()
    {
        return mSharedPreferences.getString(SHARED_PREFS_USER_NAME, "");
    }

    public void clearAllPrefs()
    {
        SharedPreferences.Editor mEditor =  mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }
}
