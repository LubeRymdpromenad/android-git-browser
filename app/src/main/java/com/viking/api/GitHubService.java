package com.viking.api;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by lars@harbourfront.se
 */

public class GitHubService {
    private static final int RESPONSE_CODE_OK = 200;
    private static GitHubService INSTANCE;

    private final ClientApi api;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(ClientApi.class);
    }

    public Single<List<Repo>> fetchRepos(@NonNull final String userName,
                                         @NonNull final String password) {
        String authHeader = okhttp3.Credentials.basic(userName, password);
        return api.getRepos(authHeader, userName);
    }

    public Single<User> fetchUser(@NonNull final String userName,
                                         @NonNull final String password) {
        String authHeader = okhttp3.Credentials.basic(userName, password);
        return api.getUser(authHeader);
    }

    public interface GitHubServiceCallback<T> {
        void onSuccess(T data);
        void onFailed();
    }
}
