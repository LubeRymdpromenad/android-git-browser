package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.api.Repo;
import com.viking.mvp.Presenter;

import java.util.List;

/**
 * Created by lars@harbourfront.se
 */

public class RepoListPresenter implements Presenter {

    private final RepoListView mView;
    private final RepoListModel mModel;

    public RepoListPresenter(@NonNull final RepoListView view, @NonNull final RepoListModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onResume() {
        if (mModel.isEmpty()) {
            mModel.fetchRepos(new RepoListModel.CallBack() {
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
                public void onFailed() {
                    mView.displayError();
                }
            });

        } else {
            mView.updateRepoList(mModel.getRepos());
        }
    }

    @Override
    public void onPause() {

    }

    public void openRepoDetailView(final int repoId) {
        mView.openRepoDetailView(repoId);
    }
}
