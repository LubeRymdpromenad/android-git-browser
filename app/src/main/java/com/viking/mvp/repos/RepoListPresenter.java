package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.api.Repo;
import com.viking.mvp.Presenter;

import java.util.List;

import io.reactivex.SingleObserver;
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
//        if (mModel.isEmpty()) {
        mModel.fetchRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Repo> repoList) {
                        mModel.storeRepoList(repoList);

                        if (repoList.isEmpty()) {
                            mView.displayEmptyMessage();
                        } else {
                            mView.updateRepoList(repoList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.displayError();
                    }
                });
//    }
    }

    @Override
    public void onPause() {
        if (mRepoDisposable != null) mRepoDisposable.dispose();
    }

    public void openRepoDetailView(final int repoId) {
        mView.openRepoDetailView(repoId);
    }
}
