package com.viking.mvp.details;

import android.support.annotation.NonNull;

import com.viking.api.Repo;
import com.viking.mvp.Presenter;

/**
 * Created by lars@harbourfront.se
 */

public class RepoDetailsPresenter implements Presenter {
    private final RepoDetailsView mView;
    private final RepoDetailsModel mModel;
    private int mRepoId;

    public RepoDetailsPresenter(@NonNull final RepoDetailsView view, RepoDetailsModel model) {
        mView = view;
        mModel = model;
    }

    public void setId(final int repoId) {
        mRepoId = repoId;
    }

    @Override
    public void onResume() {
        final Repo repo = mModel.getRepo(mRepoId);

        if (repo == null) {
            mView.displayError();
        } else {
            mView.updateRepo(repo);
        }
    }

    @Override
    public void onPause() {

    }
}
