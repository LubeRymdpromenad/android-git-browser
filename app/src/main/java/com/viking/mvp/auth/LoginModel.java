package com.viking.mvp.auth;

import android.support.annotation.NonNull;

import com.viking.api.GitHubService;
import com.viking.api.User;
import com.viking.api.UserRepository;

import io.reactivex.Single;

/**
 * Created by lars@harbourfront.se
 */

class LoginModel {
    private final UserRepository mUserRepository;

    LoginModel(@NonNull final UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    Single<User> fetchUser(@NonNull String userName, @NonNull final String password) {
        return GitHubService.getInstance().fetchUser(userName, password);
    }

    void storeCredentials(@NonNull final String userName, @NonNull final String password) {
        mUserRepository.storeCredentials(userName, password);
    }
}
