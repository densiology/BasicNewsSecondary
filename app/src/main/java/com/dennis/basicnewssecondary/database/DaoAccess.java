package com.dennis.basicnewssecondary.database;

import com.dennis.basicnewssecondary.database.tables.Favorite;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DaoAccess {

    @Query("SELECT * FROM Favorite ORDER BY date DESC")
    LiveData<List<Favorite>> getFavorites();

    @Query("SELECT * FROM Favorite WHERE id = :id LIMIT 1")
    LiveData<Favorite> getFavorite(String id);

    @Insert
    long saveFavorite(Favorite item);

    @Query("DELETE FROM Favorite WHERE id = :id")
    void deleteFavorite(String id);

}
