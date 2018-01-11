package com.viking.mvp.repos;

import com.viking.api.Repo;

import java.util.List;

/**
 * Created by lars@harbourfront.se
 */

interface RepoListView {
    void openRepoDetailView(final int repoId);
    void updateRepoList(final List<Repo> repoList);
    void displayError();
    void displayEmptyMessage();
}
