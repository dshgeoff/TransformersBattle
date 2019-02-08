package com.aequilibrium.transformersbattle.general.network;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VolleyNetworkUtil implements NetworkUtil {

    private final static int DEFAULT_TIMEOUT = 40 * 1000;
    private final static int MAX_RETRIES = 0;
    private final static int BACKOFF_MULT = 0;

    private final RequestQueue mRequestQueue;
    private static VolleyNetworkUtil mInstance;
    private final List<OnRequestFinishListener> mOnRequestFinishListeners;
    private Context mContext;

    public static VolleyNetworkUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyNetworkUtil(context);
        }

        return mInstance;
    }

    public Context getContext() {
        return mContext;
    }

    private VolleyNetworkUtil(Context context) {
        mContext = context;
        mOnRequestFinishListeners = new ArrayList<>();
        CookieHandler.setDefault(new CookieManager());
        mRequestQueue = Volley.newRequestQueue(context, new CustomUrlStack());
        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                synchronized (mOnRequestFinishListeners) {
                    Iterator<OnRequestFinishListener> iterator = mOnRequestFinishListeners.iterator();
                    while (iterator.hasNext()) {
                        OnRequestFinishListener listener = iterator.next();
                        if (listener.getRequest() == request) {
                            if (request.isCanceled()) {
                                listener.onRequestFinished();
                            }
                            iterator.remove();
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public <RESPONSE> void requestJson(
            int method,
            Map<String, String> params,
            byte[] body,
            String bodyContentType,
            APIModule apiModule,
            Class<RESPONSE> responseClass,
            ITransformersObjectParser hsbcObjectParser,
            JSONCallback callback,
            String tag) {
        cancel(tag);

        String url = apiModule.getUrl();

        StringBuilder screenLogStringBuilder = new StringBuilder();
        screenLogStringBuilder.append("==================================Start Request==================================\n\n");
        if (method == Request.Method.GET) {
            url = appendGETParams(url, params);
            Log.d("VolleyNetworkUtil", "url >>> " + url);
        }
        screenLogStringBuilder.append(String.format("Request URL: \n%s\n\n", url));
        String methodString = "";
        if (method == Request.Method.GET) {
            methodString = "GET";
        } else if (method == Request.Method.POST) {
            methodString = "POST";
        } else if (method == Request.Method.PUT) {
            methodString = "PUT";
        } else if (method == Request.Method.DELETE) {
            methodString = "DELETE";
        }
        screenLogStringBuilder.append(String.format("Method: \n%s\n\n", methodString));

        Map<String, String> header = apiModule.getHeader();

        if (header != null) {
            Log.d("VolleyNetworkUtil", "headers >>> " + header.toString());
            screenLogStringBuilder.append(String.format("Headers: \n%s\n\n", header));
        }
        if (body != null) {
            try {
                screenLogStringBuilder.append(String.format("Body: \n%s\n\n", new String(body, "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (params != null) {
            Log.d("VolleyNetworkUtil", "params >>> " + params.toString());
            screenLogStringBuilder.append(String.format("Payload: \n%s\n\n", params));
        }
        screenLogStringBuilder.append("==================================End Request==================================");
        Log.d("VolleyNetworkUtil", screenLogStringBuilder.toString());

        Bundle bundle = JSONRequest.getBundle(responseClass, hsbcObjectParser);

        JSONRequest request = null;

        try {
            request = JSONRequest.getInstance(
                    JSONRequest.class,
                    method,
                    url,
                    params,
                    header,
                    body,
                    bodyContentType,
                    callback,
                    callback,
                    bundle
            );
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_TIMEOUT,
                MAX_RETRIES,
                BACKOFF_MULT));
        request.setTag(tag);
        request.setShouldCache(false);
        synchronized (mOnRequestFinishListeners) {
            mOnRequestFinishListeners.add(new OnRequestFinishListener(callback, request));
        }
        mRequestQueue.add(request);
    }

    @Override
    public void requestString(int method, byte[] body, String bodyContentType, APIModule apiModule, StringCallback callback, String tag) {
        requestString(method, null, apiModule, body, bodyContentType, callback, tag);
    }

    @Override
    public void requestString(
            int method,
            Map<String, String> params,
            APIModule apiModule,
            StringCallback callback,
            String tag) {
        requestString(method, params, apiModule, null, null, callback, tag);
    }

    @Override
    public void requestString(int method, Map<String, String> params, APIModule apiModule, byte[] body, String bodyContentType, StringCallback callback, String tag) {
        cancel(tag);

        String url = apiModule.getUrl();

        switch (apiModule.getAction()) {
            case GetTransformer:
            case DeleteTransformer:
                url = url + params.get("id");
                params = null;
        }

        StringBuilder screenLogStringBuilder = new StringBuilder();
        Log.d("VolleyNetworkUtil", "==================================Start Request==================================");
        screenLogStringBuilder.append("==================================Start Request==================================\n");
        if (method == Request.Method.GET) {
            url = appendGETParams(url, params);
        }
        Log.d("VolleyNetworkUtil", "url >>> " + url);
        screenLogStringBuilder.append(String.format("Request URL: \n%s\n", url));
        String methodString = "";
        if (method == Request.Method.GET) {
            methodString = "GET";
        } else if (method == Request.Method.POST) {
            methodString = "POST";
        } else if (method == Request.Method.PUT) {
            methodString = "PUT";
        } else if (method == Request.Method.DELETE) {
            methodString = "DELETE";
        }
        screenLogStringBuilder.append(String.format("Method: \n%s\n\n", methodString));

        Map<String, String> header = apiModule.getHeader();

        if (header != null) {
            Log.d("VolleyNetworkUtil", "headers >>> " + header.toString());
            screenLogStringBuilder.append(String.format("Headers: \n%s\n", header));
        }
        if (body != null) {
            try {
                String bodyString = new String(body, "UTF-8");
                Log.d("VolleyNetworkUtil", "body >>> " + bodyString);
                screenLogStringBuilder.append(String.format("Body: \n%s\n", bodyString));
            } catch (Exception e) {
            }
        }
        if (params != null) {
            Log.d("VolleyNetworkUtil", "params >>> " + params.toString());
            screenLogStringBuilder.append(String.format("Payload: \n%s\n", params));
        }
        Log.d("VolleyNetworkUtil", "==================================End Request==================================");
        screenLogStringBuilder.append("==================================End Request==================================");
        Log.d("VolleyNetworkUtil", screenLogStringBuilder.toString());

        StringRequest request = null;
        try {
            request = StringRequest.getInstance(StringRequest.class, method,
                    url,
                    params,
                    apiModule.getHeader(),
                    body,
                    bodyContentType,
                    callback,
                    callback,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_TIMEOUT,
                MAX_RETRIES,
                BACKOFF_MULT));
        request.setTag(tag);
        request.setShouldCache(false);
        synchronized (mOnRequestFinishListeners) {
            mOnRequestFinishListeners.add(new OnRequestFinishListener(callback, request));
        }
        mRequestQueue.add(request);
    }

    @Override
    public void cancel(final String tag) {
        synchronized (mRequestQueue) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    if (request.getTag() == null || TextUtils.isEmpty(tag)) return false;

                    return request.getTag().toString().equals(tag);
                }
            });
        }
    }

    private String appendGETParams(String url, Map<String, String> params) {
        if (params != null) {
            String paramsStr = encodeParameters(params, "UTF-8");
            url += "?" + paramsStr;
        }
        return url;
    }

    private String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private class OnRequestFinishListener {
        private ITransformersBaseCallback mCallback;
        private Request mRequest;

        OnRequestFinishListener(ITransformersBaseCallback callback, Request request) {
            mCallback = callback;
            mRequest = request;
        }

        void onRequestFinished() {
            mCallback.onCancelled();
        }

        Request getRequest() {
            return mRequest;
        }
    }
}