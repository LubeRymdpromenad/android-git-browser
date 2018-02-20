package com.viking;

import android.support.annotation.NonNull;

import com.viking.mvp.Presenter;

/**
 * Created by lars@harbourfront.se
 */

public class MainActivityPresenter implements Presenter {
    private final MainActivityView mView;
    private final MainActivityModel mModel;
    private boolean onRestoreInstance = false;

    MainActivityPresenter(@NonNull final MainActivityView view, @NonNull final MainActivityModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onResume() {
        if (mModel.isLoggedIn() && onRestoreInstance) {
            mView.openRestoreFragment();
            onRestoreInstance = false;
        } else if (mModel.isLoggedIn()) {
            mView.openRepoListView();
        } else {
            mView.openLoginView();
        }
    }

    @Override
    public void onPause() {}

    void logout() {
        mModel.clearCredentials();
        mModel.clearRepoList();
        mView.openLoginView();
    }

    void onRestoreInstanceState() {
        onRestoreInstance = true;
    }
}
