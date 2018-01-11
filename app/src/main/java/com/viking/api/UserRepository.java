package com.viking.api;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import static com.viking.storage.Local.PREF_KEY_PASSWORD;
import static com.viking.storage.Local.PREF_KEY_USER_NAME;

/**
 * Created by lars@harbourfront.se
 */

public class UserRepository {
    private final SharedPreferences mSharedPreferences;

    public UserRepository(@NonNull final SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public void storeCredentials(@NonNull final String userName, @NonNull final String password) {
        mSharedPreferences.edit().putString(PREF_KEY_USER_NAME, userName).apply();
        mSharedPreferences.edit().putString(PREF_KEY_PASSWORD, password).apply();
    }

    public String getUserName() {
        return mSharedPreferences.getString(PREF_KEY_USER_NAME, "");
    }

    public String getPassword() {
        return mSharedPreferences.getString(PREF_KEY_PASSWORD, "");
    }

    public boolean isLoggedIn() {
        return !mSharedPreferences.getString(PREF_KEY_USER_NAME, "").isEmpty() &&
                !mSharedPreferences.getString(PREF_KEY_PASSWORD, "").isEmpty();
    }

    public void clearCredentials() {
        mSharedPreferences.edit().clear();
    }
}
