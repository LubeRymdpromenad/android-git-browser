package com.viking.mvp.details;

import android.support.annotation.NonNull;

import com.viking.api.Repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
public class RepoDetailsPresenterTest {

    @Mock
    private RepoDetailsView mView;

    @Mock
    private RepoDetailsModel mModel;

    private RepoDetailsPresenter mPresenter;
    private Repo mRepo;
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

        mPresenter = new RepoDetailsPresenter(mView, mModel);
        mRepo = new Repo();
    }

    @Test
    public void shouldPassOnResume() {
        //Given
        when(mModel.getRepo(validId)).thenReturn(Single.just(mRepo));
        mPresenter.setId(validId);

        //When
        mPresenter.onResume();

        //Then
        verify(mView).updateRepo(mRepo);
    }

    @Test
    public void shouldFailOnResume() {
        //Given
        when(mModel.getRepo(invalidId)).thenReturn(Single.error(new Throwable()));
        mPresenter.setId(invalidId);

        //When
        mPresenter.onResume();

        //Then
        verify(mView).displayError();
    }
}