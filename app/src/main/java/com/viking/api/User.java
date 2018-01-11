package com.viking.api;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by lars@harbourfront.se
 */

public class User {

    @ColumnInfo(name = "user_id")
    public int id;

    @ColumnInfo(name = "user_name")
    public String login;

    @ColumnInfo(name = "avatar_url")
    public String avatar_url;

    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public String name;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String hireable;
    public String bio;
    public String created_at;
    public String updated_at;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public int private_gists;
    public int total_private_repos;
    public int owned_private_repos;
    public int disk_usage;
    public int collaborators;
    public boolean site_admin;
    public boolean two_factor_authentication;
}
