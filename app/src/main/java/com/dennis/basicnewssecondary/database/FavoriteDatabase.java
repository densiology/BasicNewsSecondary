package com.dennis.basicnewssecondary.database;

import com.dennis.basicnewssecondary.database.tables.Favorite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
