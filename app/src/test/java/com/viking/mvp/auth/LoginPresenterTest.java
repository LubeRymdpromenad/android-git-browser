package com.viking.mvp.auth;

import com.viking.api.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by lars@harbourfront.se
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView mView;

    @Mock
    LoginModel mModel;

    private LoginPresenter mPresenter;
    private String userName = "Berra";
    private String password = "abc123";
    private User user = new User();

    @Before
    public void setUp() throws Exception {
        mPresenter = new LoginPresenter(mView, mModel);
    }

    @Test
    public void shouldPassForgotPassword() {
        mPresenter.onForgotPasswordClicked();
        Mockito.verify(mView).displayForgotPassword();
    }

    @Test
    public void shouldFailLogin() {
        Mockito.doAnswer(invocation -> {
            ((LoginModel.CallBack)invocation.getArguments()[2]).onFailed();
            return null;
        }).when(mModel).fetchUser(Mockito.isA(String.class), Mockito.isA(String.class), Mockito.isA(LoginModel.CallBack.class));

        mPresenter.onLoginClicked(userName, password);

        Mockito.verify(mView).displayError();
    }

    @Test
    public void shouldPassLogin() {
        Mockito.doAnswer(invocation -> {
            ((LoginModel.CallBack)invocation.getArguments()[2]).onSuccess(user);
            return null;
        }).when(mModel).fetchUser(Mockito.isA(String.class), Mockito.isA(String.class), Mockito.isA(LoginModel.CallBack.class));

        mPresenter.onLoginClicked(userName, password);

        Mockito.verify(mView).openRepoListView();
    }
}