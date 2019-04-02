package com.dennis.basicnewssecondary.viewmodel;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.adapters.lists.FavoritesAdapter;
import com.dennis.basicnewssecondary.database.repositories.FavoriteRepository;
import com.dennis.basicnewssecondary.database.tables.Favorite;
import com.dennis.basicnewssecondary.utilities.Common;

import java.util.List;

import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritesViewModel extends ViewModel {

    private FavoritesAdapter favoritesAdapter;
    private MutableLiveData<List<Favorite>> favoritesMutable;

    private MutableLiveData<Favorite> selectedClick;
    private MutableLiveData<Integer> selectedLongClick;

    public void init() {
        favoritesAdapter = new FavoritesAdapter(this);
        favoritesMutable = new MutableLiveData<>();
        selectedClick = new MutableLiveData<>();
        selectedLongClick = new MutableLiveData<>();
    }

    public FavoritesAdapter getFavoritesAdapter() {
        return favoritesAdapter;
    }

    public String getImageUrl(int index) {
        String imgFileName = favoritesAdapter.getFavorites().get(index).getBase_filename();
        if (imgFileName != null) {
            imgFileName = imgFileName.replaceAll(" ", Common.LINK_SPACE);
        }

        return favoritesAdapter.getFavorites().get(index).getBase_url()
                + Common.LINK_PHOTO_PREFIX
                + imgFileName;
    }

    public String getTitle(int index) {
        return favoritesAdapter.getFavorites().get(index).getTitle();
    }

    public String getDate(int index) {
        return favoritesAdapter.getFavorites().get(index).getDate();
    }

    public String getTeaser(int index) {
        return favoritesAdapter.getFavorites().get(index).getTeaser();
    }

    public MutableLiveData<Favorite> getSelectedClick() {
        return selectedClick;
    }

    public void setSelectedClick(MutableLiveData<Favorite> selectedClick) {
        this.selectedClick = selectedClick;
    }

    public MutableLiveData<List<Favorite>> getFavoritesMutable() {
        return favoritesMutable;
    }

    public void setFavoritesMutable(MutableLiveData<List<Favorite>> favoritesMutable) {
        this.favoritesMutable = favoritesMutable;
    }

    public MutableLiveData<Integer> getSelectedLongClick() {
        return selectedLongClick;
    }

    public void onItemClick(int position) {
        selectedClick.setValue(favoritesAdapter.getFavorites().get(position));
    }

    public boolean onItemLongClick(int position) {
        selectedLongClick.setValue(position);
        return false;
    }
}
