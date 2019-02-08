package com.aequilibrium.transformersbattle.data.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class BaseEntity {
    public Map<String, String> getKeyValueMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        Map<String, String> paramMap = gson.fromJson(
                json,
                new TypeToken<Map<String, String>>() {
                }.getType());
        return paramMap;
    }
}
