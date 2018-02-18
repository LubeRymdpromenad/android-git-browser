package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.mvp.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lars@harbourfront.se
 */
public class RepoListPresenter implements Presenter {

    private final RepoListView mView;
    private final RepoListModel mModel;
    private Disposable mRepoDisposable;


    public RepoListPresenter(@NonNull final RepoListView view, @NonNull final RepoListModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onResume() {
        //TODO check if we shuold check db first
//        if (mModel.isEmpty()) {
        mModel.fetchRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repoList -> {

                    if (repoList.isEmpty()) {
                        mView.displayEmptyMessage();
                    } else {
                        mModel.storeRepoList(repoList);
                        mView.updateRepoList(repoList);
                    }
                }, throwable -> mView.displayError());
    }

    @Override
    public void onPause() {
        if (mRepoDisposable != null) mRepoDisposable.dispose();
    }

    public void openRepoDetailView(final int repoId) {
        mView.openRepoDetailView(repoId);
    }
}
