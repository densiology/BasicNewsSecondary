package com.dennis.basicnewssecondary.network.clients;

import com.dennis.basicnewssecondary.network.apis.NewsAPI;
import com.dennis.basicnewssecondary.utilities.Common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {

    private static NewsAPI newsAPI;

    public static NewsAPI getNewsAPI() {
        if (newsAPI == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Common.LINK_NEWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            newsAPI = retrofit.create(NewsAPI.class);
        }
        return newsAPI;
    }

}