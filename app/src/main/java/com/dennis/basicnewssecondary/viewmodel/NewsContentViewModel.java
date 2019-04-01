package com.dennis.basicnewssecondary.viewmodel;

import com.dennis.basicnewssecondary.models.NewsItemModel;

import androidx.lifecycle.ViewModel;

public class NewsContentViewModel extends ViewModel {

    private NewsItemModel itemModel;

    public void init(NewsItemModel itemModel) {
        this.itemModel = itemModel;
    }

    public NewsItemModel getItemModel() {
        return itemModel;
    }
}
