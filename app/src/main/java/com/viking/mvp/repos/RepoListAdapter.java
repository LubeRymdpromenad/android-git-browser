package com.viking.mvp.repos;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.viking.R;
import com.viking.api.Repo;
import com.viking.databinding.ViewRepoListItemBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by lars@harbourfront.se
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder> {

    private final RepoListPresenter mPresenter;
    private List<Repo> mRepoList = Collections.emptyList();

    public RepoListAdapter(@NonNull final RepoListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public RepoListViewHolder onCreateViewHolder(@Nullable ViewGroup viewGroup, final int i) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        final ViewRepoListItemBinding binding
                = DataBindingUtil.inflate(inflater, R.layout.view_repo_list_item, viewGroup, true);

        return new RepoListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RepoListViewHolder viewHolder, final int i) {
        final Repo repo = mRepoList.get(i);

        viewHolder.binding.tvName.setText(repo.name);

        viewHolder.binding.tvName.setOnClickListener(view -> mPresenter.openRepoDetailView(repo.id));
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    public class RepoListViewHolder extends RecyclerView.ViewHolder {
        public ViewRepoListItemBinding binding;

        public RepoListViewHolder(@NonNull final ViewRepoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setRepoList(@NonNull final List<Repo> repoList) {
        mRepoList = repoList;
        notifyDataSetChanged();
    }
}
