package com.dennis.basicnewssecondary.network.clients;

import com.dennis.basicnewssecondary.network.apis.NewsAPI;
import com.dennis.basicnewssecondary.utilities.Common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {

    private static NewsAPI newsAPI;

    public static NewsAPI getNewsAPI() {
        if (newsAPI == null) {
/*
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                            NewsListModel.class,
                            new JsonNewsListModelDeserializer())
                    .create();
*/
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Common.LINK_NEWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            newsAPI = retrofit.create(NewsAPI.class);
        }
        return newsAPI;
    }

/*
    private Retrofit retrofit;
    private Object service;

    public NewsClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Common.LINK_NEWS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        if (service == null || !serviceClass.isInstance(service)) {
            service = retrofit.create(serviceClass);
        }
        return (T) service;
    }
    */

}