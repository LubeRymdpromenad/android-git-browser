package com.viking;

import android.support.annotation.NonNull;

import com.viking.api.UserRepository;
import com.viking.storage.GitBrowserDatabase;

/**
 * Created by lars@harbourfront.se
 */

public class MainActivityModel {
    private final UserRepository mUserRepository;
    private final GitBrowserDatabase mDatabase;

    public MainActivityModel(@NonNull final UserRepository userRepository, @NonNull final GitBrowserDatabase database) {
        mUserRepository = userRepository;
        mDatabase = database;
    }

    public boolean isLoggedIn() {
        return mUserRepository.isLoggedIn();
    }

    public void clearCredentials() {
        mUserRepository.clearCredentials();
    }

    public void clearRepoList() {
        mDatabase.repoDao().nukeTable();
    }
}
