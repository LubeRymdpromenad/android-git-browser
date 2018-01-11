package com.viking.mvp.details;

import com.viking.api.Repo;

/**
 * Created by lars@harbourfront.se
 */

interface RepoDetailsView {
    void updateRepo(Repo repo);
    void displayError();
}
