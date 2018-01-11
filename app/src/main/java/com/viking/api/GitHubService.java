package com.viking.api;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lars@harbourfront.se
 */

public class GitHubService {
    private static final int RESPONSE_CODE_OK = 200;
    private static GitHubService INSTANCE;

    private ClientApi api;

    public static GitHubService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GitHubService();
        }
        return INSTANCE;
    }

    private GitHubService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ClientApi.class);
    }

    public <T> void fetchRepos(@NonNull final String userName,
                               @NonNull final String password,
                               @NonNull final GitHubServiceCallback<T> callback) {

        String authHeader = okhttp3.Credentials.basic(userName, password);

        Call<List<Repo>> call = api.getRepos(authHeader, userName);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                int statusCode = response.code();
                if(statusCode != RESPONSE_CODE_OK) {
                    callback.onFailed();
                    return;
                }

                final List<Repo> repoList = response.body();
                callback.onSuccess((T) repoList);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                callback.onFailed();
            }
        });
    }

    public <T> void fetchUser(@NonNull final String userName,
                              @NonNull final String password,
                              @NonNull final GitHubServiceCallback<T> callback) {

        String authHeader = okhttp3.Credentials.basic(userName, password);

        Call<User> call = api.getUser(authHeader);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                if(statusCode != RESPONSE_CODE_OK) {
                    callback.onFailed();
                    return;
                }

                final User user = response.body();
                callback.onSuccess((T) user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailed();
            }
        });
    }

    public interface GitHubServiceCallback<T> {
        void onSuccess(T data);
        void onFailed();
    }
}
