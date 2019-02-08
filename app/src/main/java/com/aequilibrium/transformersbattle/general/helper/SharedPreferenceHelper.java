package com.aequilibrium.transformersbattle.general.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aequilibrium.transformersbattle.general.interfaces.ISharedPreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SharedPreferenceHelper implements ISharedPreferenceHelper {
    private static SharedPreferenceHelper INSTANCE = null;

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;
    private Gson mGson;
    private Gson mGsonWithoutUrlEncode;

    private String name = "";

    private static final String SHARED_PREFERENCE_ALLSPARK = "SHARED_PREFERENCE_ALLSPARK";

    public static SharedPreferenceHelper getInstance(String name, Context context) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        if (INSTANCE == null || !INSTANCE.name.equals(name))
            INSTANCE = new SharedPreferenceHelper(name, context);
        return INSTANCE;
    }

    private SharedPreferenceHelper(String name, Context context) {
        this.name = name;
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mGson = new Gson();
        mGsonWithoutUrlEncode = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public boolean saveAllSpark(String allSpark) {
        return mEditor.putString(SHARED_PREFERENCE_ALLSPARK, allSpark).commit();
    }

    @Override
    public String getAllSpark() {
        return mSharedPreferences.getString(SHARED_PREFERENCE_ALLSPARK, "");
    }
}
