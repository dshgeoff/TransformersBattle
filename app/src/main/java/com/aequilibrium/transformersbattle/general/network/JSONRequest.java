package com.aequilibrium.transformersbattle.general.network;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JSONRequest<RESPONSE> extends BaseRequest<RESPONSE, JSONRequest<RESPONSE>> implements IJSONRequest<RESPONSE> {

    private Class<RESPONSE> mResponseClass;
    private ITransformersObjectParser mObjectParser;

    public static final String RESPONSE_CLASS = "RESPONSE_CLASS";
    public static final String OBJECT_PARSER = "OBJECT_PARSER";

    public JSONRequest(
            int method,
            String url,
            Map<String, String> params,
            Map<String, String> headers,
            byte[] body,
            String bodyContentType,
            Response.Listener<RESPONSE> listener,
            Response.ErrorListener errorListener,
            Bundle arg) {
        super(method, url, params, headers, body, bodyContentType, listener, errorListener, arg);
    }


    public static Bundle getBundle(Class responseClass, ITransformersObjectParser hsbcObjectParser) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESPONSE_CLASS, responseClass);
        bundle.putSerializable(OBJECT_PARSER, hsbcObjectParser);
        return bundle;
    }

    @Override
    protected Response<RESPONSE> parseNetworkResponse(NetworkResponse response) {
        String json = "";
        StringBuilder screenLogStringBuilder = new StringBuilder();
        screenLogStringBuilder.append("==================================Start Response==================================\n\n");
        screenLogStringBuilder.append(String.format("Request URL: \n%s\n\n", getUrl()));
        screenLogStringBuilder.append(String.format("Response Code: \n%s\n\n", response.statusCode));
        screenLogStringBuilder.append(String.format("Header from Response: \n%s\n\n", response.headers));
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, getParamsEncoding()));
            Log.d("JSONRequest", "json >>> " + json);
            screenLogStringBuilder.append(String.format("Response: \n%s\n\n", json));
            screenLogStringBuilder.append("==================================End Response==================================");
            Log.d("JSONRequest", screenLogStringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

        try {
            return parseToClass(json, mResponseClass, response);
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Response<RESPONSE> parseToClass(String json, Class<RESPONSE> clazz, NetworkResponse response) {
        return Response.success(mObjectParser.parseObject(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
    }
}
