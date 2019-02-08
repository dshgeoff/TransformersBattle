package com.aequilibrium.transformersbattle.general.network;

import android.content.Context;

public class APIManager {
    public static NetworkUtil getNetworkUtil(Context context) {
        return VolleyNetworkUtil.getInstance(context);
    }
}
