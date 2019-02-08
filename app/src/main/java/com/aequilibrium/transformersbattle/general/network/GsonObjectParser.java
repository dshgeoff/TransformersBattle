package com.aequilibrium.transformersbattle.general.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonObjectParser implements ITransformersObjectParser {

    @Override
    public <RESPONSE> RESPONSE parseObject(String json, Class<RESPONSE> responseClass) {
        try {
            return new Gson().fromJson(json, responseClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    @Override
    public String toJsonString(Object object) {
        try {
            return new Gson().toJson(object);
        } catch (Exception e) {
            return null;
        }
    }
}