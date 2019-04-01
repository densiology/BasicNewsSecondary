package com.dennis.basicnewssecondary.network.apis;

import com.dennis.basicnewssecondary.models.NewsListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsAPI {

    @GET("get_hashtag{newsPage}.json.gz")
    Call<NewsListModel> getNews(@Path("newsPage") int page);

}