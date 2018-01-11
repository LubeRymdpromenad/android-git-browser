package com.viking.mvp.details;

import android.support.annotation.NonNull;

import com.viking.api.Repo;
import com.viking.storage.GitBrowserDatabase;

/**
 * Created by lars@harbourfront.se
 */

public class RepoDetailsModel {

    private final GitBrowserDatabase mDataBase;

    public RepoDetailsModel(@NonNull final GitBrowserDatabase database) {
        mDataBase = database;
    }

    public Repo getRepo(final int id) {
        return mDataBase.repoDao().get(id);
    }
}
