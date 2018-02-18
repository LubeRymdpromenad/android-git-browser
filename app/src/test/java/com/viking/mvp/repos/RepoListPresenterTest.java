package com.viking.mvp.repos;

import android.support.annotation.NonNull;

import com.viking.api.Repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lars@harbourfront.se
 */

@RunWith(MockitoJUnitRunner.class)
public class RepoListPresenterTest {

    @Mock
    private RepoListView mView;

    @Mock
    private RepoListModel mModel;

    private RepoListPresenter mPresenter;
    private final List<Repo> mEmptyRepoList = Collections.emptyList();
    private final int invalidId = -1;
    private final int validId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

        mPresenter = new RepoListPresenter(mView, mModel);
    }

    @Test
    public void shouldFailOnResume() {
        //Given
        when(mModel.fetchRepos()).thenReturn(Single.error(new Throwable()));

        //When
        mPresenter.onResume();

        //Then
        verify(mView).displayError();
    }

    @Test
    public void shouldPassOnResumeEmptyResponse() {
        //Given
        when(mModel.fetchRepos()).thenReturn(Single.just(mEmptyRepoList));

        //When
        mPresenter.onResume();

        //Then
        verify(mView).displayEmptyMessage();
    }

    @Test
    public void shouldPassOnResumeNotEmptyResponse() {
        //Given
        List<Repo> repoList = new ArrayList<>();
        repoList.add(new Repo());
        when(mModel.fetchRepos()).thenReturn(Single.just(repoList));

        //When
        mPresenter.onResume();

        //Then
        verify(mModel).storeRepoList(repoList);
        verify(mView).updateRepoList(repoList);
    }

    @Test
    public void passOpenDetailView() {
        //When
        mPresenter.openRepoDetailView(validId);

        //Then
        verify(mView).openRepoDetailView(validId);
    }
}