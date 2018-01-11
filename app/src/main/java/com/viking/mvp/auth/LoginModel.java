package com.viking.mvp.auth;

import android.support.annotation.NonNull;

import com.viking.api.GitHubService;
import com.viking.api.User;
import com.viking.api.UserRepository;

/**
 * Created by lars@harbourfront.se
 */

public class LoginModel {
    private final UserRepository mUserRepository;

    public LoginModel(@NonNull final UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void fetchUser(@NonNull final String userName, @NonNull final String password, CallBack callBack) {
        GitHubService.getInstance().fetchUser(userName, password, new GitHubService.GitHubServiceCallback<User>() {
            @Override
            public void onSuccess(User user) {
                callBack.onSuccess(user);
            }

            @Override
            public void onFailed() {
                callBack.onFailed();
            }
        });
    }

    public void storeCredentials(@NonNull final String userName, @NonNull final String password) {
        mUserRepository.storeCredentials(userName, password);
    }

    interface CallBack {
        void onSuccess(User user);
        void onFailed();
    }
}
