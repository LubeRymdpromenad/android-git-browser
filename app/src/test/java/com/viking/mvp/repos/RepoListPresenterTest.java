package com.viking.mvp.repos;

import com.viking.api.Repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by lars@harbourfront.se
 */

@RunWith(MockitoJUnitRunner.class)
public class RepoListPresenterTest {

    @Mock
    RepoListView mView;

    @Mock
    RepoListModel mModel;

    private RepoListPresenter mPresenter;
    private final int mValidRepoId = 0;
    private List<Repo> mEmptyRepoList = Collections.emptyList();

    @Before
    public void setUp() throws Exception {
        mPresenter = new RepoListPresenter(mView, mModel);
    }

    @Test
    public void shouldPassOnResumeFullResponse() {
        when(mModel.isEmpty()).thenReturn(true);

        final List<Repo> mLocalRepoList = new ArrayList<>();
        mLocalRepoList.add(new Repo());

        Mockito.doAnswer(invocation -> {
            ((RepoListModel.CallBack)invocation.getArguments()[0]).onSuccess(mLocalRepoList);
            return null;
        }).when(mModel).fetchRepos(Mockito.isA(RepoListModel.CallBack.class));

        mPresenter.onResume();
        Mockito.verify(mView).updateRepoList(mLocalRepoList);
    }

    @Test
    public void shouldPassOnResumeEmptyResponse() {
        when(mModel.isEmpty()).thenReturn(true);

        Mockito.doAnswer(invocation -> {
            ((RepoListModel.CallBack)invocation.getArguments()[0]).onSuccess(mEmptyRepoList);
            return null;
        }).when(mModel).fetchRepos(Mockito.isA(RepoListModel.CallBack.class));

        mPresenter.onResume();
        Mockito.verify(mView).displayEmptyMessage();
    }

    @Test
    public void shouldFailOnResumeEmptyDatabase() {
        when(mModel.isEmpty()).thenReturn(true);

        Mockito.doAnswer(invocation -> {
            ((RepoListModel.CallBack)invocation.getArguments()[0]).onFailed();
            return null;
        }).when(mModel).fetchRepos(Mockito.isA(RepoListModel.CallBack.class));

        mPresenter.onResume();
        Mockito.verify(mView).displayError();
    }

    @Test
    public void shouldPassOnResumeFullDatabase() {
        when(mModel.isEmpty()).thenReturn(false);
        when(mModel.getRepos()).thenReturn(mEmptyRepoList);

        mPresenter.onResume();

        Mockito.verify(mView).updateRepoList(mEmptyRepoList);
    }

    @Test
    public void passOpenDetailView() {
        mPresenter.openRepoDetailView(mValidRepoId);
        Mockito.verify(mView).openRepoDetailView(mValidRepoId);
    }
}