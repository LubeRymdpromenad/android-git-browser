package com.viking;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.viking.api.UserRepository;
import com.viking.mvp.auth.LoginFragment;
import com.viking.mvp.details.RepoDetailsFragment;
import com.viking.mvp.repos.RepoListFragment;
import com.viking.storage.GitBrowserDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final String KEY_CURRENT_FRAGMENT = "key-current-fragment";

    private LoginFragment mLoginFragment;
    private ViewDataBinding mBinding;
    private RepoListFragment mRepoListFragment;
    private RepoDetailsFragment mRepoDetailsFragment;
    private MainActivityPresenter mPresenter;
    private Fragment mCurrentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        final UserRepository userRepository = new UserRepository(sharedPreferences);
        final GitBrowserDatabase database = GitBrowserDatabase.getGitBrowserDatabase(this);
        final MainActivityModel repository = new MainActivityModel(userRepository, database);
        mPresenter = new MainActivityPresenter(this, repository);

        if (savedInstanceState != null) {
            restoreFragments(savedInstanceState);
            mPresenter.onRestoreInstanceState();
        }
    }

    private void restoreFragments(@NonNull final Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        mLoginFragment = (LoginFragment) fragmentManager.findFragmentByTag(LoginFragment.class.getName());
        mRepoListFragment = (RepoListFragment) fragmentManager.findFragmentByTag(RepoListFragment.class.getName());
        mRepoDetailsFragment = (RepoDetailsFragment) fragmentManager.findFragmentByTag(RepoDetailsFragment.class.getName());
        mCurrentFragment = fragmentManager.getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, KEY_CURRENT_FRAGMENT, getCurrentFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    public void addFragment(@NonNull final Fragment fragment) {
        addFragment(fragment, true);
    }

    public void addFragment(@NonNull final Fragment fragment, final boolean addToBackStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setRetainInstance(true);
        transaction.replace(R.id.main_container, fragment, fragment.getClass().getName());

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mPresenter.logout();
                break;
            default:
                break;
        }

        return true;
    }

    public void openRestoreFragment() {
        boolean addToBackStack = !(mCurrentFragment instanceof LoginFragment);
        addFragment(mCurrentFragment, addToBackStack);
    }

    public void openLoginView() {
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
        }
        addFragment(mLoginFragment, false);
    }

    public void openRepoListView() {
        if (mRepoListFragment == null) {
            mRepoListFragment = RepoListFragment.newInstance();
        }
        addFragment(mRepoListFragment);
    }

    public void openRepoDetailsView(final int repoId) {
        mRepoDetailsFragment = RepoDetailsFragment.newInstance(repoId);
        addFragment(mRepoDetailsFragment);
    }

    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if(fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
