package com.viking.mvp.auth;

import android.support.annotation.NonNull;

import com.viking.api.User;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lars@harbourfront.se
 */

class LoginPresenter {
    private final LoginView mView;
    private final LoginModel mModel;

    public LoginPresenter(@NonNull final LoginView view, @NonNull final LoginModel model) {
        mView = view;
        mModel = model;
    }

    public void onLoginClicked(@NonNull final String userName, @NonNull final String password) {
        mModel.fetchUser(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        mModel.storeCredentials(userName, password);
                        mView.openRepoListView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.displayError();
                    }
                });
    }

    public void onForgotPasswordClicked() {
        mView.displayForgotPassword();
    }
}
