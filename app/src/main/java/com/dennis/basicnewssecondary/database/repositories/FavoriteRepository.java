package com.dennis.basicnewssecondary.database.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.dennis.basicnewssecondary.database.FavoriteDatabase;
import com.dennis.basicnewssecondary.database.tables.Favorite;
import com.dennis.basicnewssecondary.models.NewsItemModel;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class FavoriteRepository {

    private String DB_NAME = "BasicNewsSecondary";
    private FavoriteDatabase favoriteDatabase;

    public FavoriteRepository(Context context) {
        favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabase.class, DB_NAME).build();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return favoriteDatabase.daoAccess().getFavorites();
    }

    public LiveData<Favorite> getFavorite(String id) {
        return favoriteDatabase.daoAccess().getFavorite(id);
    }

    public boolean saveFavorite(NewsItemModel item) {
        final Favorite favorite = new Favorite();
        favorite.setId(item.getId());
        favorite.setTitle(item.getTitle());
        favorite.setMain(item.getMain());
        favorite.setTeaser(item.getTeaser());
        favorite.setDate(item.getDate());
        favorite.setBase_url(item.getBase_url());
        favorite.setBase_filename(item.getBase_filename());
        return saveFavorite(favorite) != -1;
    }

    public void deleteFavorite(final String id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                favoriteDatabase.daoAccess().deleteFavorite(id);
                return null;
            }
        }.execute();
    }

    private long saveFavorite(final Favorite favorite) {
        try {
            return new AsyncTask<Void, Void, Long>() {
                @Override
                protected Long doInBackground(Void... voids) {
                    return favoriteDatabase.daoAccess().saveFavorite(favorite);
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
