package com.viking.mvp.repos;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viking.MainActivity;
import com.viking.R;
import com.viking.api.Repo;
import com.viking.api.UserRepository;
import com.viking.databinding.FragmentRepoListBinding;
import com.viking.storage.GitBrowserDatabase;

import java.util.List;

/**
 * Created by lars@harbourfront.se
 */

public class RepoListFragment extends Fragment implements RepoListView {

    private FragmentRepoListBinding mBinding;
    private RepoListPresenter mPresenter;
    private RepoListAdapter mReposListAdapter;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );

        final SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        final UserRepository userRepository = new UserRepository(sharedPreferences);
        final GitBrowserDatabase database = GitBrowserDatabase.getGitBrowserDatabase(getContext());
        final RepoListModel repoListModel = new RepoListModel(userRepository, database);
        mPresenter = new RepoListPresenter(this, repoListModel);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.rvRepoList.setLayoutManager(new LinearLayoutManager(getContext()));

        mReposListAdapter = new RepoListAdapter(mPresenter);
        mBinding.rvRepoList.setAdapter(mReposListAdapter);
    }

    public static RepoListFragment newInstance() {
        final RepoListFragment fragment = new RepoListFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    public void openRepoDetailView(final int repoId) {
        if (getActivity() != null) {
            ((MainActivity)getActivity()).openRepoDetailsView(repoId);
        }
    }

    @Override
    public void updateRepoList(List<Repo> repoList) {
        mReposListAdapter.setRepoList(repoList);
    }

    @Override
    public void displayError() {
        //TODO Add a retry or go back in view
        Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.download_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayEmptyMessage() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.no_repos_message, Snackbar.LENGTH_LONG).show();
    }
}
