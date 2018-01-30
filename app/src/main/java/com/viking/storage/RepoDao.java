package com.viking.storage;

/**
 * Created by lars@harbourfront.se
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.viking.api.Repo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface RepoDao {

    @Query("SELECT * FROM repo")
    Single<List<Repo>> getAll();

    @Query("SELECT * FROM repo where id LIKE :id")
    Single<Repo> getRepo(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Repo> repos);

    @Query("DELETE FROM repo")
    void nukeTable();
}
