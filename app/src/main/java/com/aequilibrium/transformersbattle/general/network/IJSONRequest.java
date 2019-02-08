package com.aequilibrium.transformersbattle.general.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

public interface IJSONRequest<RESPONSE> {
    Response<RESPONSE> parseToClass(String json, Class<RESPONSE> clazz, NetworkResponse response);
}