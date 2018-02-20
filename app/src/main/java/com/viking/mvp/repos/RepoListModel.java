package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.api.GitHubService;
import com.viking.api.Repo;
import com.viking.api.UserRepository;
import com.viking.storage.GitBrowserDatabase;

import java.util.List;

import io.reactivex.Single;


/**
 * Created by lars@harbourfront.se
 */

class RepoListModel {
    private final UserRepository mUserRepository;
    private final GitBrowserDatabase mDatabase;

    RepoListModel(@NonNull final UserRepository userRepository, @NonNull final GitBrowserDatabase database) {
        mUserRepository = userRepository;
        mDatabase = database;
    }

    /* TODO Check database first */
    boolean isEmpty() {
        return true;
//        return mDatabase.repoDao().getAll().isEmpty();
    }

    public Single<List<Repo>> getRepos() {
        return mDatabase.repoDao().getAll();
    }

    Single<List<Repo>> fetchRepos() {
        final String userName = mUserRepository.getUserName();
        final String password = mUserRepository.getPassword();
        return GitHubService.getInstance().fetchRepos(userName, password);
    }

    void storeRepoList(@NonNull final List<Repo> repoList) {
        mDatabase.repoDao().insertAll(repoList);
    }
}
