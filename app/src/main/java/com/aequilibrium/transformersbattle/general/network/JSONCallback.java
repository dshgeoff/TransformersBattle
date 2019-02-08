package com.aequilibrium.transformersbattle.general.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public abstract class JSONCallback<RESPONSE> implements ITransformersJSONCallback<RESPONSE>, Response.Listener<RESPONSE>, Response.ErrorListener {

    @Override
    public void onResponse(RESPONSE response) {
        onRespond();
        onSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onRespond();
        onError();
    }
}

