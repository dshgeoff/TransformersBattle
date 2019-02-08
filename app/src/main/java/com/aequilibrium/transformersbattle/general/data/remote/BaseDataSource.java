package com.aequilibrium.transformersbattle.general.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.general.helper.SharedPreferenceHelper;
import com.google.gson.Gson;

public class BaseDataSource {

    private Context mContext;
    private Gson mGson;
    protected SharedPreferenceHelper mSharedPreferenceHelper;

    protected BaseDataSource(@NonNull Context context) {
        mContext = context;
        mGson = new Gson();
        mSharedPreferenceHelper = SharedPreferenceHelper.getInstance(mContext.getString(R.string.sharedpreference_name), context);
    }

    protected Context getContext() {
        return mContext;
    }

    protected Gson getGson() {
        return mGson;
    }

    protected SharedPreferenceHelper getSharedPreferenceHelper() {
        if (mSharedPreferenceHelper == null) {
            mSharedPreferenceHelper = SharedPreferenceHelper.getInstance(mContext.getString(R.string.sharedpreference_name), getContext());
        }
        return mSharedPreferenceHelper;
    }
}
