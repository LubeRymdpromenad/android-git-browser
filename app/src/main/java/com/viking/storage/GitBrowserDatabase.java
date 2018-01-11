package com.viking.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.viking.api.Repo;

/**
 * Created by lars@harbourfront.se
 */

@Database(entities = {Repo.class}, version = 1)
public abstract class GitBrowserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "git-browser-db";

    private static GitBrowserDatabase INSTANCE;

    public abstract RepoDao repoDao();

    public static GitBrowserDatabase getGitBrowserDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GitBrowserDatabase.class, DATABASE_NAME)
                    //TODO replace with worker thread (asyncTask)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}