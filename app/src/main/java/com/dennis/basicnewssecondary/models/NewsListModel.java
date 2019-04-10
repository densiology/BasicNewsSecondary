package com.dennis.basicnewssecondary.models;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.network.clients.NewsClient;

import java.io.IOException;
import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListModel {

    private ArrayList<NewsItemModel> stories;   // for deserializing purposes only, don't put data
    private int page_number;
    private int total_pages;

    private MutableLiveData<ArrayList<NewsItemModel>> storiesMutable = new MutableLiveData<>(); // don't initialize the Arraylist, else the observer will be called twice
    private MutableLiveData<Integer> fetchFail = new MutableLiveData<>();

    public int getPage_number() {
        return page_number;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public MutableLiveData<ArrayList<NewsItemModel>> getStoriesMutable() {
        return storiesMutable;
    }

    public MutableLiveData<Integer> getFetchFail() {
        return fetchFail;
    }

    public void fetchList(int page) {
        NewsClient.getNewsAPI().getNews(page).enqueue(new Callback<NewsListModel>() {
            @Override
            public void onResponse(Call<NewsListModel> call, Response<NewsListModel> response) {
                page_number = response.body().page_number;
                total_pages = response.body().total_pages;

                if (storiesMutable.getValue() == null || page_number == 1) {
                    storiesMutable.setValue(response.body().stories);
                } else {
                    storiesMutable.getValue().remove(null);
                    storiesMutable.getValue().addAll(response.body().stories);
                    storiesMutable.setValue(storiesMutable.getValue());
                }
            }

            @Override
            public void onFailure(Call<NewsListModel> call, Throwable t) {
                if (t instanceof IOException) {
                    fetchFail.setValue(R.string.toast_error_no_connection);
                } else {
                    fetchFail.setValue(R.string.toast_error);
                }
            }
        });
    }
}
