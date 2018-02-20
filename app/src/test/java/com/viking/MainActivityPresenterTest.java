package com.viking;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lars@harbourfront.se
 */

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    private MainActivityView mView;

    @Mock
    private MainActivityModel mModel;

    private MainActivityPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mPresenter = new MainActivityPresenter(mView, mModel);
    }

    @Test
    public void shouldPassOnResumeLoggedOut() {
        //Given
        when(mModel.isLoggedIn()).thenReturn(false);

        //When
        mPresenter.onResume();

        //Then
        verify(mView).openLoginView();
    }

    @Test
    public void shouldPassOnResumeLoggedIn() {
        //Given
        when(mModel.isLoggedIn()).thenReturn(true);

        //When
        mPresenter.onResume();

        //Then
        verify(mView).openRepoListView();
    }

    @Test
    public void shouldPassOnResumeLoggedInOnRestoreInstance() {
        //Given
        when(mModel.isLoggedIn()).thenReturn(true);
        mPresenter.onRestoreInstanceState();

        //When
        mPresenter.onResume();

        //Then
        verify(mView).openRestoreFragment();
    }

    @Test
    public void shouldPassLogout() {
        //When
        mPresenter.logout();

        //Then
        verify(mModel).clearCredentials();
        verify(mModel).clearRepoList();
        verify(mView).openLoginView();
    }
}