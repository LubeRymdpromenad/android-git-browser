package com.viking.mvp.details;

import android.support.annotation.NonNull;

import com.viking.api.Repo;
import com.viking.storage.GitBrowserDatabase;

import io.reactivex.Single;

/**
 * Created by lars@harbourfront.se
 */

class RepoDetailsModel {

    private final GitBrowserDatabase mDataBase;

    RepoDetailsModel(@NonNull final GitBrowserDatabase database) {
        mDataBase = database;
    }

    Single<Repo> getRepo(final int id) {
        return mDataBase.repoDao().getRepo(id);
    }
}
