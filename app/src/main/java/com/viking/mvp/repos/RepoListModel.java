package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.api.GitHubService;
import com.viking.api.Repo;
import com.viking.api.UserRepository;
import com.viking.storage.GitBrowserDatabase;

import java.util.List;

/**
 * Created by lars@harbourfront.se
 */

public class RepoListModel {
    private final UserRepository mUserRepository;
    private final GitBrowserDatabase mDatabase;

    public RepoListModel(@NonNull final UserRepository userRepository, @NonNull final GitBrowserDatabase database) {
        mUserRepository = userRepository;
        mDatabase = database;
    }

    public boolean isEmpty() {
        return mDatabase.repoDao().getAll().isEmpty();
    }

    public List<Repo> getRepos() {
        return mDatabase.repoDao().getAll();
    }

    public void fetchRepos(@NonNull final CallBack callBack) {
        final String userName = mUserRepository.getUserName();
        final String password = mUserRepository.getPassword();
        if (userName.isEmpty() || password.isEmpty()) {
            callBack.onFailed();
        }

        GitHubService.getInstance().fetchRepos(userName, password, new GitHubService.GitHubServiceCallback<List<Repo>>() {
            @Override
            public void onSuccess(List<Repo> repoList) {
                callBack.onSuccess(repoList);
            }

            @Override
            public void onFailed() {
                callBack.onFailed();
            }
        });
    }

    public void storeRepoList(@NonNull final List<Repo> repoList) {
        mDatabase.repoDao().insertAll(repoList);
    }

    interface CallBack {
        void onSuccess(List<Repo> repoList);
        void onFailed();
    }
}
