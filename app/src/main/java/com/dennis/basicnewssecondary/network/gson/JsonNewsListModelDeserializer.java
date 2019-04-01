package com.dennis.basicnewssecondary.network.gson;

import com.dennis.basicnewssecondary.models.NewsListModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class JsonNewsListModelDeserializer implements JsonDeserializer<NewsListModel> {
    @Override
    public NewsListModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
