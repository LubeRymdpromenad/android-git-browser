package com.viking.mvp.details;

import com.viking.api.Repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by lars@harbourfront.se
 */

@RunWith(MockitoJUnitRunner.class)
public class RepoDetailsPresenterTest {

    @Mock
    RepoDetailsView mView;

    @Mock
    RepoDetailsModel mModel;

    private RepoDetailsPresenter mPresenter;
    private Repo mRepo;
    private int invalidId = -1;
    private int validId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mPresenter = new RepoDetailsPresenter(mView, mModel);
        mRepo = new Repo();

        when(mModel.getRepo(invalidId)).thenReturn(null);
        when(mModel.getRepo(validId)).thenReturn(mRepo);
    }

    @Test
    public void shouldPassOnResume() {
        mPresenter.setId(validId);
        mPresenter.onResume();
        Mockito.verify(mView).updateRepo(mRepo);
    }

    @Test
    public void shouldFailOnResume() {
        mPresenter.setId(invalidId);
        mPresenter.onResume();
        Mockito.verify(mView).displayError();
    }
}