package com.viking;

import android.support.annotation.NonNull;

import com.viking.api.UserRepository;
import com.viking.storage.GitBrowserDatabase;

/**
 * Created by lars@harbourfront.se
 */

class MainActivityModel {
    private final UserRepository mUserRepository;
    private final GitBrowserDatabase mDatabase;

    MainActivityModel(@NonNull final UserRepository userRepository, @NonNull final GitBrowserDatabase database) {
        mUserRepository = userRepository;
        mDatabase = database;
    }

    boolean isLoggedIn() {
        return mUserRepository.isLoggedIn();
    }

    void clearCredentials() {
        mUserRepository.clearCredentials();
    }

    void clearRepoList() {
        mDatabase.repoDao().nukeTable();
    }
}
