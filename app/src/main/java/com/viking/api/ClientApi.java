package com.viking.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * Created by lars@harbourfront.se
 */

interface ClientApi {
    String BASE_URL = "https://api.github.com";

    //TODO move headers to Interceptor
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    Single<User> getUser(@Header("Authorization") String authHeader);

    @Headers("Accept: application/vnd.github.v3+json")
    //TODO Add paging
    @GET("/users/{userName}/repos")
    Single<List<Repo>> getRepos(@Header("Authorization") String authHeader, @Path("userName") String userName);
}
