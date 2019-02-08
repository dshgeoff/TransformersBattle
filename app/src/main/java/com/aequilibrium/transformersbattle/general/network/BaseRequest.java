package com.aequilibrium.transformersbattle.general.network;

import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public abstract class BaseRequest<RESPONSE, T extends BaseRequest<RESPONSE, T>> extends Request<RESPONSE> {

    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private byte[] mBody;
    private Response.Listener<RESPONSE> mListener;
    private String mBodyContentType;

    public BaseRequest(
            int method,
            String url,
            Map<String, String> params,
            Map<String, String> headers,
            byte[] body,
            String bodyContentType,
            Response.Listener<RESPONSE> listener,
            Response.ErrorListener errorListener,
            Bundle args) {
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
        mBody = body;
        mBodyContentType = bodyContentType;
        mHeaders = headers;
    }

    public static <RequestClass extends BaseRequest> RequestClass getInstance(
            Class<RequestClass> requestClass,
            int method,
            String url,
            Map<String, String> params,
            Map<String, String> headers,
            byte[] body,
            String bodyContentType,
            Response.Listener listener,
            Response.ErrorListener errorListener,
            Bundle args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] constructor = new Class[9];
        constructor[0] = Integer.TYPE;
        constructor[1] = String.class;
        constructor[2] = Map.class;
        constructor[3] = Map.class;
        constructor[4] = byte[].class;
        constructor[5] = String.class;
        constructor[6] = Response.Listener.class;
        constructor[7] = Response.ErrorListener.class;
        constructor[8] = Bundle.class;
        return (RequestClass) requestClass.getDeclaredConstructor(constructor).newInstance(method, url, params, headers, body, bodyContentType, listener, errorListener, args);
    }

    @Override
    protected void deliverResponse(RESPONSE response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {

        if (error != null && error.networkResponse != null) {
            String sessionError = parseXML(error.networkResponse.headers.get("errorMessage"));
            if (!TextUtils.isEmpty(parseXML(sessionError)) || error.networkResponse.data.length != 0) {
                Response<RESPONSE> response = parseNetworkResponse(error.networkResponse);
                if (response.result != null) {
                    deliverResponse(response.result);
                    return;
                }
            }
        }
        super.deliverError(error);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (this.getMethod() == Method.POST) {
            return mParams;
        } else {
            return super.getParams();
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaders != null) {
            return mHeaders;
        } else {
            return super.getHeaders();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mBody != null && mBody.length != 0) {
            return mBody;
        } else {
            return super.getBody();
        }
    }

    @Override
    public String getBodyContentType() {
        if (!TextUtils.isEmpty(mBodyContentType)) {
            return mBodyContentType;
        }
        return super.getBodyContentType();
    }


    public String parseXML(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document document = documentBuilder.parse(is);
            Element response = (Element) document.getElementsByTagName("Response").item(0);
            Element reason = (Element) response.getElementsByTagName("reasons").item(0);
            Element desc = (Element) reason.getElementsByTagName("desc").item(0);
            return desc.getFirstChild().getNodeValue();
        } catch (Exception e) {
        }
        return null;
    }
}
