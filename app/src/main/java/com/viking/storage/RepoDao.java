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

@Dao
public interface RepoDao {
    @Query("SELECT * FROM repo")
    List<Repo> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Repo> repos);

    @Query("SELECT * FROM repo where id LIKE :id")
    Repo get(int id);

    @Query("DELETE FROM repo")
    void nukeTable();
}
