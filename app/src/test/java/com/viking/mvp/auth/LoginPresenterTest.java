package com.viking.mvp.auth;

import android.support.annotation.NonNull;

import com.viking.api.User;

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
public class LoginPresenterTest {

    @Mock
    private LoginView mView;

    @Mock
    private LoginModel mModel;

    private LoginPresenter mPresenter;
    private final String userName = "Berra";
    private final String password = "abc123";
    private final User user = new User();

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

        mPresenter = new LoginPresenter(mView, mModel);
    }

    @Test
    public void shouldPassForgotPassword() {
        //When
        mPresenter.onForgotPasswordClicked();

        //Then
        verify(mView).displayForgotPassword();
    }

    @Test
    public void shouldFailLogin() {
        //Given
        when(mModel.fetchUser("", password)).thenReturn(Single.error(new Throwable()));

        //When
        mPresenter.onLoginClicked("", password);

        //Then
        verify(mView).displayError();
    }


    @Test
    public void shouldPassLogin() {
        //Given
        when(mModel.fetchUser(userName, password)).thenReturn(Single.just(new User()));

        //When
        mPresenter.onLoginClicked(userName, password);

        //When
        verify(mModel).storeCredentials(userName, password);
        verify(mView).openRepoListView();
    }
}