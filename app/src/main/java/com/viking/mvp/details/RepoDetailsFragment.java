package com.viking.mvp.details;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viking.R;
import com.viking.api.Repo;
import com.viking.databinding.FragmentRepoDetailsBinding;
import com.viking.storage.GitBrowserDatabase;


/**
 * Created by lars@harbourfront.se
 */

public class RepoDetailsFragment extends Fragment implements RepoDetailsView {
    private static final String ARGS_REPO_ID = "args-repo-id";
    private RepoDetailsPresenter mPresenter;
    private FragmentRepoDetailsBinding mBinding;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        final GitBrowserDatabase database = GitBrowserDatabase.getGitBrowserDatabase(getContext());
        final RepoDetailsModel model = new RepoDetailsModel(database);
        mPresenter = new RepoDetailsPresenter(this, model);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();

        final int repoId = getArguments().getInt(ARGS_REPO_ID);
        mPresenter.setId(repoId);
    }

    public static RepoDetailsFragment newInstance(final int repoId) {
        final RepoDetailsFragment fragment = new RepoDetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(ARGS_REPO_ID, repoId);
        fragment.setArguments(bundle);
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

    private void setupListeners() {
        mBinding.tvSshUrl.setOnClickListener(this::onClickCopy);
        mBinding.tvCloneUrl.setOnClickListener(this::onClickCopy);
    }

    @Override
    public void updateRepo(@NonNull final Repo repo) {
        mBinding.tvName.setText(repo.name);
        mBinding.tvDescription.setText(repo.description);
        final String language = repo.language != null && !repo.language.isEmpty() ? repo.language : getString(R.string.not_available);
        mBinding.tvLang.setText(language);
        mBinding.tvWatchers.setText(String.valueOf(repo.watchers));
        mBinding.tvSshUrl.setText(repo.ssh_url);
        mBinding.tvCloneUrl.setText(repo.clone_url);
    }

    @Override
    public void displayError() {
        Toast.makeText(getContext(), R.string.really_bad_error_message, Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(11)
    private void onClickCopy(View view) {   // User-defined onClick Listener
        TextView textView = (TextView) view;

        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if(sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            assert clipboard != null;
            clipboard.setText(textView.getText().toString());   // Assuming that you are copying the text from a TextView
            Toast.makeText(getContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
        }
        else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("", textView.getText().toString());
            assert clipboard != null;
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
        }
    }
}
