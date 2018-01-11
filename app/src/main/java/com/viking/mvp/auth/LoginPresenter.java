package com.viking.mvp.auth;

import android.support.annotation.NonNull;

import com.viking.api.User;

/**
 * Created by lars@harbourfront.se
 */

public class LoginPresenter {
    private final LoginView mView;
    private final LoginModel mModel;

    public LoginPresenter(@NonNull final LoginView view, @NonNull final LoginModel model) {
        mView = view;
        mModel = model;
    }

    public void onLoginClicked(@NonNull final String userName, @NonNull final String password) {
        mModel.fetchUser(userName, password, new LoginModel.CallBack() {
            @Override
            public void onSuccess(User user) {
                mModel.storeCredentials(userName, password);
                mView.openRepoListView();
            }

            @Override
            public void onFailed() {
                mView.displayError();
            }
        });
    }

    public void onForgotPasswordClicked() {
        mView.displayForgotPassword();
    }
}
