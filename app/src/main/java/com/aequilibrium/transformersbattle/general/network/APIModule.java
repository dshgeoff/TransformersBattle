package com.aequilibrium.transformersbattle.general.network;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class APIModule {

    private APIAction mAction;
    private HashMap<String, String> mUrlParams;
    private HashMap<String, String> mHeaderParams;

    private static String mAllSpark = "";

    public APIModule(APIAction action) {
        mAction = action;
        mUrlParams = null;
        mHeaderParams = null;
    }

    public static String getAllSpark() {
        return mAllSpark;
    }

    public static void setAllSpark(String allSpark) {
        mAllSpark = allSpark;
    }

    public String getUrl() {
        return "https://transformers-api.firebaseapp.com/" + mAction.toString();
    }

    public Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        if (!TextUtils.isEmpty(mAllSpark)) {
            header.put("Authorization", "Bearer " + mAllSpark);
        }
        return header;
    }

    public APIAction getAction() {
        return mAction;
    }
}
