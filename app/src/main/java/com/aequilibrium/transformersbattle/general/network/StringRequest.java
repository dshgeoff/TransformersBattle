package com.aequilibrium.transformersbattle.general.network;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class StringRequest extends BaseRequest<TransformersResponse, StringRequest> {

    public StringRequest(
            int method,
            String url,
            Map<String, String> params,
            Map<String, String> headers,
            byte[] body,
            String bodyContentType,
            Response.Listener<TransformersResponse> listener,
            Response.ErrorListener errorListener,
            Bundle arg) {
        super(method, url, params, headers, body, bodyContentType, listener, errorListener, arg);
    }

    @Override
    protected Response<TransformersResponse> parseNetworkResponse(NetworkResponse response) {
        String json = "";
        StringBuilder screenLogStringBuilder = new StringBuilder();
        screenLogStringBuilder.append("==================================Start Response==================================\n\n");
        screenLogStringBuilder.append(String.format("Request URL: \n%s\n\n", getUrl()));
        screenLogStringBuilder.append(String.format("Response Code: \n%s\n\n", response.statusCode));
        screenLogStringBuilder.append(String.format("Header from Response: \n%s\n\n", response.headers));
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, getParamsEncoding()));
            Log.d("StringRequest", "json >>> " + json);
            screenLogStringBuilder.append(String.format("Response: \n%s\n\n", json));
            screenLogStringBuilder.append("==================================End Response==================================");
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }

        TransformersResponse respond = new TransformersResponse();
        respond.header = response.headers;
        respond.json = json;
        try {
            respond.setTransformersError(parseXML(response.headers.get("errorMessage")));
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(json) && (response.statusCode == 200 || response.statusCode == 201)) {
            //FIXME Hard code json String if the respond is 200/201 and return nothing
            respond.json = "{}";
        }
        return Response.success(respond, HttpHeaderParser.parseCacheHeaders(response));
    }

}
