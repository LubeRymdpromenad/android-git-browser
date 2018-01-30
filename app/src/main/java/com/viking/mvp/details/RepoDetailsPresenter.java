package com.viking.mvp.details;

import android.support.annotation.NonNull;

import com.viking.mvp.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lars@harbourfront.se
 */

public class RepoDetailsPresenter implements Presenter {
    private final RepoDetailsView mView;
    private final RepoDetailsModel mModel;
    private int mRepoId;
    private Disposable mRepoDisposable;

    RepoDetailsPresenter(@NonNull final RepoDetailsView view, RepoDetailsModel model) {
        mView = view;
        mModel = model;
    }

    public void setId(final int repoId) {
        mRepoId = repoId;
    }

    @Override
    public void onResume() {
        mRepoDisposable = mModel.getRepo(mRepoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::updateRepo, throwable -> mView.displayError());
    }

    @Override
    public void onPause() {
        if (mRepoDisposable != null) mRepoDisposable.dispose();
    }
}
