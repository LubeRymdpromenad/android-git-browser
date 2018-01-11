package com.viking.api;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by lars@harbourfront.se
 */

@Entity(tableName = "repo")
public class Repo {

    @PrimaryKey
    public int id;

    @Embedded
    public User owner;

    @ColumnInfo(name = "repo_name")
    public String name;

    @ColumnInfo(name = "repo_full_name")
    public String full_name;

    @ColumnInfo(name = "repo_description")
    public String description;

    @ColumnInfo(name = "repo_language")
    public String language;

    @ColumnInfo(name = "repo_watchers_count")
    public int watchers_count;

    @ColumnInfo(name = "repo_watchers")
    public int watchers;

    @ColumnInfo(name = "repo_git_url")
    public String git_url;

    @ColumnInfo(name = "repo_ssh_url")
    public String ssh_url;

    @ColumnInfo(name = "repo_clone_url")
    public String clone_url;
}
